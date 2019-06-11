package brian.wang.spring.demo.quartz.domain;

import com.google.common.base.Strings;
import lombok.Data;
import org.quartz.*;

import java.text.ParseException;

import static com.google.common.base.Preconditions.checkArgument;

@Data
public class TriggerDAO {

    private String name;
    private String group;
    private String cronExpression;
    private String description;

    public CronTrigger convert2QuartzTrigger(JobDetail jobDetail){
        CronExpression ce = null;
        try {
            checkArgument(!Strings.isNullOrEmpty(cronExpression),"illegal cronExpression");
            ce= new CronExpression(this.cronExpression);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(ce))
                .withIdentity(this.name,this.group)
                .withDescription(this.description)
                .build();
    }
}
