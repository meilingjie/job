package com.leo.job.interfaces.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leo.job.handle.SchedulerEngine;
import com.leo.job.model.TaskDef;
import com.leo.job.model.TaskInstance;
import com.leo.job.model.TaskParamValue;
import com.leo.job.services.TaskDefService;
import com.leo.job.services.TaskInstanceService;
import com.leo.job.services.TaskLogic;
import com.leo.job.services.TaskParamValueService;
import com.leo.job.support.TaskInstanceModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author leo
 * @Description
 * @date 15/12/16 下午3:33
 */

@Controller
@RequestMapping("job")
public class TaskController {

    private static Logger LOG = LogManager.getLogger(TaskController.class);
    @Inject
    private TaskInstanceService taskInstanceService;
    @Inject
    private TaskDefService taskDefService;
    @Inject
    private TaskParamValueService taskParamValueService;
    @Inject
    private ApplicationContext appContext;

    @RequestMapping(value = "/list")
    public String list(Model model) {
        List<TaskInstance> taskList = taskInstanceService.queryAllTaskInstances();
        List<TaskDef> taskDefList = taskDefService.queryTaskDefList();
        model.addAttribute("taskList", taskList);
        model.addAttribute("taskDefList", taskDefList);
        return "task/list";
    }

    @RequestMapping(value = "/reboot")
    @ResponseBody
    public void reboot() {
        SchedulerEngine schedulerEngine = appContext.getBean("schedulerEngine", SchedulerEngine.class);
        schedulerEngine.reboot();
        LOG.debug("JobKernel reboot jobPool success.....");
    }

    @RequestMapping(value = "/saveOrUpdateTaskDef")
    public String saveOrUpdateTaskDef(@ModelAttribute("taskDef") TaskDef taskDef) {
        taskDef.setPrivateFlag((short) 1);
        taskDef.setTaskType(1);
        taskDefService.insertTaskDef(taskDef);
        return "redirect:/job/list";
    }

    @RequestMapping(value = "/saveOrUpdateTaskIns")
    public String saveOrUpdateTaskIns(@ModelAttribute("taskInstance") TaskInstance taskInstance) {
        taskInstance.setPrivateFlag((short) 1);
        taskInstance.setNotifyMode(1);
        int flag;
        boolean operateFlag;
        if (taskInstance.getId() == null) {
            taskInstance.setLastDoneTime(new Date());
            taskInstance.setNextRunTime(new Date());
            flag = taskInstanceService.insertTaskInstance(taskInstance);
            operateFlag = true;
        } else {
            flag = taskInstanceService.updateTaskInstance(taskInstance);
            operateFlag = false;
        }

        //加入task
        if (flag > 0 && taskInstance.getAutoFlag() == 1) {
            TaskDef taskDef = taskDefService.queryTaskDefById(taskInstance.getTaskDefId());
            TaskLogic taskLogic = appContext.getBean(taskDef.getAlgorithm(), TaskLogic.class);

            SchedulerEngine schedulerEngine = appContext.getBean("schedulerEngine", SchedulerEngine.class);

            TaskInstanceModel tm = new TaskInstanceModel();
            tm.setTaskInstance(taskInstance);
            List<TaskParamValue> list = taskParamValueService.queryTaskParamValuesByTaskInsId(taskInstance.getId());
            tm.setParamVals(list);

            if (operateFlag) {
                schedulerEngine.addJob(tm, taskLogic);
            } else {
                schedulerEngine.updateJob(tm, taskLogic);
            }

        } else if (flag > 0 && taskInstance.getAutoFlag() == 0) {
            SchedulerEngine schedulerEngine = appContext.getBean("schedulerEngine", SchedulerEngine.class);

            TaskInstanceModel tm = new TaskInstanceModel();
            tm.setTaskInstance(taskInstance);
            List<TaskParamValue> list = taskParamValueService.queryTaskParamValuesByTaskInsId(taskInstance.getId());
            tm.setParamVals(list);

            if (!operateFlag) {
                schedulerEngine.removeJob(tm);
            }

        }
        return "redirect:/job/list";
    }

    @RequestMapping(value = "/deleteTaskIns")
    public String deleteTaskIns(Long id) {
        TaskInstance taskInstance = taskInstanceService.queryTaskInstanceById(id);
        int flag = taskInstanceService.deleteTaskInstanceById(id);

        //task移除
        if (flag > 0) {
            SchedulerEngine schedulerEngine = appContext.getBean("schedulerEngine", SchedulerEngine.class);

            TaskInstanceModel tm = new TaskInstanceModel();
            tm.setTaskInstance(taskInstance);
            List<TaskParamValue> list = taskParamValueService.queryTaskParamValuesByTaskInsId(taskInstance.getId());
            tm.setParamVals(list);

            schedulerEngine.removeJob(tm);
        }
        return "redirect:/job/list";
    }

    @RequestMapping(value = "/saveOrUpdateTaskParam")
    @ResponseBody
    public JSONObject saveOrUpdateTaskParam(@ModelAttribute("taskParam") TaskParamValue taskParam) {
        taskParam.setParamType(1);
        JSONObject jo = new JSONObject();
        int count;
        if (taskParam.getId() != null) {
            count = taskParamValueService.updateTaskParam(taskParam);
        } else {
            count = taskParamValueService.insertTaskParam(taskParam);
            jo.put("taskParamId", taskParam.getId());
        }

        if (count > 0) {
            TaskInstance taskInstance = taskInstanceService.queryTaskInstanceById(taskParam.getTaskInsId());
            TaskDef taskDef = taskDefService.queryTaskDefById(taskInstance.getTaskDefId());
            TaskLogic taskLogic = appContext.getBean(taskDef.getAlgorithm(), TaskLogic.class);

            SchedulerEngine schedulerEngine = appContext.getBean("schedulerEngine", SchedulerEngine.class);

            TaskInstanceModel tm = new TaskInstanceModel();
            tm.setTaskInstance(taskInstance);
            List<TaskParamValue> list = taskParamValueService.queryTaskParamValuesByTaskInsId(taskInstance.getId());
            tm.setParamVals(list);

            schedulerEngine.updateJob(tm, taskLogic);
        }
        jo.put("status", count > 0 ? "SUCCESS" : "FAIL");
        return jo;
    }

    @RequestMapping(value = "/deleteTaskParam")
    @ResponseBody
    public JSONObject deleteTaskParam(Long id, Long taskInsId) {
        int flag = taskParamValueService.deleteTaskParamById(id);

        if (flag > 0) {
            TaskInstance taskInstance = taskInstanceService.queryTaskInstanceById(taskInsId);
            TaskDef taskDef = taskDefService.queryTaskDefById(taskInstance.getTaskDefId());
            TaskLogic taskLogic = appContext.getBean(taskDef.getAlgorithm(), TaskLogic.class);

            SchedulerEngine schedulerEngine = appContext.getBean("schedulerEngine", SchedulerEngine.class);

            TaskInstanceModel tm = new TaskInstanceModel();
            tm.setTaskInstance(taskInstance);
            List<TaskParamValue> list = taskParamValueService.queryTaskParamValuesByTaskInsId(taskInstance.getId());
            tm.setParamVals(list);

            schedulerEngine.updateJob(tm, taskLogic);
        }

        JSONObject jo = new JSONObject();
        jo.put("status", flag > 0 ? "SUCCESS" : "FAIL");
        return jo;
    }

    @RequestMapping(value = "/findTaskParamValuesByTaskInsId")
    @ResponseBody
    public JSONArray findTaskParamValuesByTaskInsId(@RequestParam(required = true) Long taskInsId) {
        List paramList = taskParamValueService.queryTaskParamValuesByTaskInsId(taskInsId);
        JSONArray ja = new JSONArray(paramList);
        return ja;
    }
}
