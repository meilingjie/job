package com.leo.job.repositories.impl;

import com.leo.job.model.TaskParamValue;
import com.leo.job.repositories.TaskParamValueRepository;
import com.leo.job.repositories.support.MybatisRepositorySupport;

import javax.inject.Named;
import java.util.List;

/**
 * @author leo
 * @Description
 * @date 下午11:57
 */
@Named
public class TaskParamValueRepositoryImpl extends MybatisRepositorySupport<TaskParamValue> implements TaskParamValueRepository {

    @Override
    public int insertTaskParam(TaskParamValue taskParamValue){
        return this.insert("insert", taskParamValue);
    }

    @Override
    public List<TaskParamValue> queryTaskParamValuesByTaskInsId(Long taskInsId) {
        return this.findList("queryTaskParamValuesByTaskInsId", taskInsId);
    }

    @Override
    public int deleteTaskParamById(Long id){
        return this.delete("deleteTaskParamById", id);
    }

    @Override
    public int updateTaskParam(TaskParamValue taskParamValue){
        return this.update("updateTaskParam", taskParamValue);
    }
}
