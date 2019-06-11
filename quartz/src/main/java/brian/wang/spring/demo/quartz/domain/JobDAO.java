package brian.wang.spring.demo.quartz.domain;

import brian.wang.spring.demo.quartz.component.AppConst;
import brian.wang.spring.demo.quartz.component.jobs.HttpJob;
import brian.wang.spring.demo.quartz.component.jobs.QuartzTestJob;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.core.jmx.JobDataMapSupport;
import org.slf4j.Logger;
import org.springframework.util.ClassUtils;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

@Data
public class JobDAO {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JobDAO.class);
    private static final Map<String,Class<? extends Job>> SUPPORTED_JOB_TYPES =
            new HashMap<String,Class<? extends Job>>(){
                {
                    put(AppConst.JobType.TEST_JOB, QuartzTestJob.class);
                    put(AppConst.JobType.HTTP_JOB, HttpJob.class);
                }
            };
    private static final Set<String> SUPPORTED_EXT_FIELDS = new HashSet<String>(){
        {
            add("type");    // AppConst.JobType
            add("method");  // Method Name, Please define within AppConst
            add("url"); // http invoke url
            add("jsonParams");  // method params
        }
    };

    private String name;
    private String group;
    private String targetClass;
    private String description;
    @ApiModelProperty(value = "Extension Fields",dataType = "Map[String,Object]")
    private Map<String,Object> extInfo;

    public JobDetail convert2QuartzJobDetail(){
        Class<? extends Job> clazz = null;
        if (Objects.isNull(targetClass)) {
            String type = String.valueOf(extInfo.get("type"));
            clazz = SUPPORTED_JOB_TYPES.get(type);
            checkNotNull(clazz,"Cannot find suitable job class");
            this.targetClass = clazz.getCanonicalName();
        }
        try {
            clazz = (Class<Job>) ClassUtils.resolveClassName(this.targetClass, this.getClass().getClassLoader());
        } catch (IllegalArgumentException e) {
            log.error("classloading error",e);
        }

        return JobBuilder.newJob()
                .ofType(clazz)
                .withIdentity(name,group)
                .withDescription(description)
                .setJobData(JobDataMapSupport.newJobDataMap(extInfo))
                .build();
    }
}

