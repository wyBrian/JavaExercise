package brian.wang.spring.demo.quartz.service;

import brian.wang.spring.demo.quartz.domain.JobDetailDAO;
import com.google.common.collect.Lists;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuartzJobDetailService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * Get jobs List
     * @return
     */
    @Transactional(readOnly = true)
    public List<JobDetailDAO> queryJobList(){
        List<JobDetailDAO> jobDetailDAOs = Lists.newArrayList();

        Function<Set<JobKey>,List<JobDetailDAO>> copyPropFun = jbst -> {
            List<JobDetailDAO> jddList = Lists.newArrayList();
            jddList = jbst.stream().map(jk ->{
                JobDetail jd = null;
                List<Trigger> trList = this.getTriggerByKey(jk);
                jd = this.getJobDetailByKey(jk);

                JobDetailDAO jobDetailDAO = new JobDetailDAO();
                jobDetailDAO.fillWithQuartzJobDetail.accept(jd);
                jobDetailDAO.fillWithQuartzTriggers.accept(trList);
                return jobDetailDAO;
            }).collect(Collectors.toList());
            return jddList;
        };

        try {
            Set<JobKey> jobSet = schedulerFactoryBean.getScheduler().getJobKeys(GroupMatcher.anyJobGroup());
            jobDetailDAOs = copyPropFun.apply(jobSet);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobDetailDAOs;
    }

    /**
     * Query by jobkey jobDetail
     * @param jobKey
     * @return
     */
    @Transactional(readOnly = true)
    public JobDetailDAO queryByKey(JobKey jobKey){
        JobDetailDAO jobDetailDAO = new JobDetailDAO();
        JobDetail jobDetail = this.getJobDetailByKey(jobKey);
        if (Objects.nonNull(jobDetail)) {
            List<Trigger> triggerList = this.getTriggerByKey(jobKey);
            jobDetailDAO.fillWithQuartzJobDetail.accept(jobDetail);
            jobDetailDAO.fillWithQuartzTriggers.accept(triggerList);
        }
        return jobDetailDAO;
    }

    /**
     * Add Job
     * @param jobDetailDAO
     */
    public boolean add(JobDetailDAO jobDetailDAO) {
        JobDetail jobDetail = jobDetailDAO.getJobDAO().convert2QuartzJobDetail();
        Set<CronTrigger> triggerSet = jobDetailDAO.getTriggerDAOs().stream().map(jtd ->
                jtd.convert2QuartzTrigger(jobDetail)
        ).collect(Collectors.toSet());
        try {
            schedulerFactoryBean.getScheduler().scheduleJob(jobDetail,triggerSet,true);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete Job
     * @param jobKeyList
     */
    public boolean remove(List<JobKey> jobKeyList){
        try {
            return schedulerFactoryBean.getScheduler().deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Disable macthed jobs
     * @param matcher
     * @return
     */
    public boolean disable(GroupMatcher<JobKey> matcher){
        try {
            schedulerFactoryBean.getScheduler().pauseJobs(matcher);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Disable all jobs
     * @return
     */
    public boolean disableAll(){
        try {
            schedulerFactoryBean.getScheduler().pauseAll();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enable matched jobs
     * @param  matcher
     * @return
     */
    public boolean enable(GroupMatcher<JobKey> matcher){
        try {
            schedulerFactoryBean.getScheduler().resumeJobs(matcher);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Enable all jobs
     * @return
     */
    public boolean enableAll(){
        try {
            schedulerFactoryBean.getScheduler().resumeAll();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Trigger job now
     * @param jobKey
     * @param jobDataMap
     * @return
     */
    public boolean triggerNow(JobKey jobKey, JobDataMap jobDataMap){
        try {
            schedulerFactoryBean.getScheduler().triggerJob(jobKey,jobDataMap);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get jobDetail by Key
     * @param jobKey
     * @return
     */
    @Transactional(readOnly = true)
    public JobDetail getJobDetailByKey(JobKey jobKey){
        JobDetail jd = null;
        try {
            jd = schedulerFactoryBean.getScheduler().getJobDetail(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jd;
    }

    /**
     * Get job trigger by Key
     * @param jobKey
     * @return
     */
    public List<Trigger> getTriggerByKey(JobKey jobKey){
        List<Trigger> triggerList = Lists.newArrayList();
        try {
            triggerList = (List<Trigger>)schedulerFactoryBean.getScheduler().getTriggersOfJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return triggerList;
    }
}
