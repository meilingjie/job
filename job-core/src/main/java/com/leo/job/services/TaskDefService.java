package com.leo.job.services;

import com.leo.job.model.TaskDef;

import java.util.List;

/**
 * @Description 任务定义 service
 * @author leo
 * @date 2015年10月2日上午11:14:03
 */
public interface TaskDefService {
    List<TaskDef> queryTaskDefList();

    int insertTaskDef(TaskDef taskDef);

    TaskDef queryTaskDefById(Long id);
}
