package com.leo.job.repositories;

import com.leo.job.model.TaskDef;

import java.util.List;

/**
 * @author leo
 * @Description
 * @date 下午11:58
 */
public interface TaskDefRepository {
    List<TaskDef> queryTaskDefList();

    int insertTaskDef(TaskDef taskDef);

    TaskDef queryTaskDefById(Long id);
}
