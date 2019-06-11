package brian.wang.spring.demo.quartz.controller;

import brian.wang.spring.demo.quartz.domain.JobDetailDAO;
import brian.wang.spring.demo.quartz.service.QuartzJobDetailService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.quartz.JobKey;
import org.quartz.core.jmx.JobDataMapSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "job")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private QuartzJobDetailService quartzJobDetailService;

    @ApiOperation(value="Get job lists")
    @GetMapping
    public ResponseEntity<List<JobDetailDAO>> list(){
        List<JobDetailDAO> jobDetailDAOs = quartzJobDetailService.queryJobList();
        return ResponseEntity.ok().body(jobDetailDAOs);
    }

    @ApiOperation("get jobDetail by key")
    @ApiImplicitParams({
            @ApiImplicitParam(name="group",value="组名",required = true,dataType = "String",
                    paramType="path"),
            @ApiImplicitParam(name="name",value="名称",required = true,dataType = "String",
                    paramType="path")
    })
    @GetMapping("/{group}/{name}")
    public ResponseEntity<JobDetailDAO> queryByJobKey(
            @PathVariable String name,
            @PathVariable String group){
        JobKey jobKey = new JobKey(name,group);
        JobDetailDAO jobDetailDAO = quartzJobDetailService.queryByKey(jobKey);
        return ResponseEntity.ok().body(jobDetailDAO);
    }
    @ApiOperation("Add new Job")
    @PostMapping
    public ResponseEntity<Boolean> add(@RequestBody JobDetailDAO jobDetailDAO){
        boolean result = quartzJobDetailService.add(jobDetailDAO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @ApiOperation("delete matched jobs")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobKeyGroups",value = "批量删除的任务")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody Map<String,List<String>> jobKeyGroups){
        List<JobKey> jobKeys = Lists.newArrayList();
        jobKeyGroups.forEach((k,v) ->
                v.forEach(name -> {
                    JobKey jobKey = new JobKey(name,k);
                    jobKeys.add(jobKey);
                })
        );
        boolean result = quartzJobDetailService.remove(jobKeys);
        return ResponseEntity.ok().body(result);
    }

    @ApiOperation("trigger matched jobs now")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "group",value = "组名",required = true,dataType = "String",
                    paramType = "path"),
            @ApiImplicitParam(name = "name",value = "任务名",required = true,dataType = "String",
                    paramType = "path"),
            @ApiImplicitParam(name = "jobData",value = "额外数据",required = true,
                    dataType = "Map<String,Object>",
                    paramType = "body")
    })
    @PostMapping("/{group}/{name}")
    public ResponseEntity<Boolean> triggerNow(@PathVariable String group,
                                              @PathVariable String name,
                                              @RequestBody Map<String,Object> jobData){
        JobKey jobKey = new JobKey(name,group);
        boolean result = quartzJobDetailService.triggerNow(
                jobKey,
                JobDataMapSupport.newJobDataMap(jobData)
        );
        return ResponseEntity.ok().body(result);
    }
}
