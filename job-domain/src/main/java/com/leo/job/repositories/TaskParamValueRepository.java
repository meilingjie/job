package com.leo.job.repositories;

import com.leo.job.model.TaskParamValue;

import java.util.List;

/**
 * @author leo
 * @Description
 * @date 15/12/17 上午10:10
 */
public interface TaskParamValueRepository {
    int insertTaskParam(TaskParamValue taskParamValue);

    List<TaskParamValue> queryTaskParamValuesByTaskInsId(Long taskInsId);

    int deleteTaskParamById(Long id);

    int updateTaskParam(TaskParamValue taskParamValue);
}
