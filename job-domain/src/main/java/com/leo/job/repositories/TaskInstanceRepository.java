package com.leo.job.repositories;

import com.leo.job.model.TaskInstance;

import java.util.List;

/**
 * @author leo
 * @Description
 * @date 上午9:49
 */
public interface TaskInstanceRepository {
    int insertTaskInstance(TaskInstance taskInstance);

    List<TaskInstance> queryTaskInstanceByTaskDefId(Long taskDefId);

    List<TaskInstance> queryAllTaskInstances();

    TaskInstance queryTaskInstanceById(Long id);

    int updateTaskInstance(TaskInstance taskInstance);

    int updateTaskQueue(TaskInstance taskInstance);

    int deleteTaskInstanceById(Long id);
}
