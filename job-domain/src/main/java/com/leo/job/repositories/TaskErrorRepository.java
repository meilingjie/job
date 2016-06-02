package com.leo.job.repositories;

import com.leo.job.model.TaskError;

import java.util.List;

/**
 * @author leo
 * @Description
 * @date 下午10:34
 */
public interface TaskErrorRepository {
    int insertTaskError(TaskError taskError);

    int updateTaskError(TaskError taskError);

    List<TaskError> queryTaskError(Short exceptionType);

    List<TaskError> queryTaskErrorByDateAndType(Integer year, Integer month, Integer day, String taskCode, Short exceptionType);

    List<TaskError> queryOtherTaskError(Short taskType);

    List<TaskError> queryErrorTaskDate(String taskCode);
}
