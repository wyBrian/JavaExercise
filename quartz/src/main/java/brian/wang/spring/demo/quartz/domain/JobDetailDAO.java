package brian.wang.spring.demo.quartz.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.quartz.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ApiModel(value="JobDetailDAO", description="Quartz JodDetail Equivalent")
@Data
public class JobDetailDAO {

    @ApiModelProperty
    private JobDAO jobDAO;

    private Set<TriggerDAO> triggerDAOs;

    public transient Consumer<JobDetail> fillWithQuartzJobDetail = jd -> {

        JobDAO newJobDAO = new JobDAO();
        JobKey jk = jd.getKey();

        BeanUtils.copyProperties(jk, newJobDAO);
        newJobDAO.setDescription(jd.getDescription());
        newJobDAO.setTargetClass(jd.getJobClass().getCanonicalName());

        JobDataMap jdm= jd.getJobDataMap();
        if (Objects.nonNull(jdm)) {
            newJobDAO.setExtInfo(jdm.getWrappedMap());
        }

        setJobDAO(newJobDAO);
    };

    public transient Consumer<List<Trigger>> fillWithQuartzTriggers = trList -> {
        Set<TriggerDAO> tdSet = trList.stream().map(tr ->{
            TriggerDAO td = new TriggerDAO();
            if (tr instanceof CronTrigger) {
                CronTrigger ctr = (CronTrigger) tr;
                td.setCronExpression(ctr.getCronExpression());
            }
            TriggerKey trk = tr.getKey();
            td.setName(trk.getName());
            td.setGroup(trk.getGroup());
            td.setDescription(tr.getDescription());
            return td;
        }).collect(Collectors.toSet());
        setTriggerDAOs(tdSet);
    };
}