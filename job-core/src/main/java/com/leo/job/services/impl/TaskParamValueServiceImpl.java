package com.leo.job.services.impl;

import com.leo.job.model.TaskParamValue;
import com.leo.job.repositories.TaskParamValueRepository;
import com.leo.job.services.TaskParamValueService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author leo
 * @Description 任务定义 service
 * @date 2015年10月2日上午11:14:03
 */
@Named
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TaskParamValueServiceImpl implements TaskParamValueService {

    @Inject
    private TaskParamValueRepository taskParamValueRepository;

    @Override
    public List<TaskParamValue> queryTaskParamValuesByTaskInsId(Long taskInsId) {
        return taskParamValueRepository.queryTaskParamValuesByTaskInsId(taskInsId);
    }

    @Override
    public int insertTaskParam(TaskParamValue taskParamValue){
        return taskParamValueRepository.insertTaskParam(taskParamValue);
    }

    @Override
    public int deleteTaskParamById(Long id){
        return taskParamValueRepository.deleteTaskParamById(id);
    }

    @Override
    public int updateTaskParam(TaskParamValue taskParamValue){
        return taskParamValueRepository.updateTaskParam(taskParamValue);
    }
}
