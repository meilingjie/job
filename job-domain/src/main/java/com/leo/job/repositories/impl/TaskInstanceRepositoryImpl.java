package com.leo.job.repositories.impl;

import com.leo.job.model.TaskInstance;
import com.leo.job.repositories.TaskInstanceRepository;
import com.leo.job.repositories.support.MybatisRepositorySupport;

import javax.inject.Named;
import java.util.List;

/**
 * @author leo
 * @Description
 * @date 下午11:57
 */
@Named
public class TaskInstanceRepositoryImpl extends MybatisRepositorySupport<TaskInstance> implements TaskInstanceRepository {

    @Override
    public int insertTaskInstance(TaskInstance taskInstance){
        return this.insert("insert", taskInstance);
    }

    @Override
    public List<TaskInstance> queryTaskInstanceByTaskDefId(Long taskDefId){
        return this.findList("queryTaskInstanceByTaskDefId", taskDefId);
    }

    @Override
    public List<TaskInstance> queryAllTaskInstances(){
        return this.findList("queryAllTaskInstances");
    }

    @Override
    public TaskInstance queryTaskInstanceById(Long id){
        return this.findOne("queryTaskInstanceById", id);
    }

    @Override
    public int updateTaskInstance(TaskInstance taskInstance){
        return this.update("updateTaskInstance", taskInstance);
    }

    @Override
    public int updateTaskQueue(TaskInstance taskInstance){
        return this.update("updateTaskQueue", taskInstance);
    }

    @Override
    public int deleteTaskInstanceById(Long id){
        return this.delete("deleteTaskInstanceById", id);
    }
}
