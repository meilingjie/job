package com.leo.job.services;

import com.leo.job.model.TaskParamValue;

import java.util.List;

/**
 * @author leo
 * @Description
 * @date 15/12/17 上午10:12
 */
public interface TaskParamValueService {
    List<TaskParamValue> queryTaskParamValuesByTaskInsId(Long taskInsId);

    int insertTaskParam(TaskParamValue taskParamValue);

    int deleteTaskParamById(Long id);

    int updateTaskParam(TaskParamValue taskParamValue);
}
