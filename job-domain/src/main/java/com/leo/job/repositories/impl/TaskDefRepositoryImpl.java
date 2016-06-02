package com.leo.job.repositories.impl;

import com.leo.job.model.TaskDef;
import com.leo.job.repositories.TaskDefRepository;
import com.leo.job.repositories.support.MybatisRepositorySupport;


import javax.inject.Named;
import java.util.List;

/**
 * @author leo
 * @Description
 * @date 下午11:57
 */
@Named
public class TaskDefRepositoryImpl extends MybatisRepositorySupport<TaskDef> implements TaskDefRepository {

	@Override
    public List<TaskDef> queryTaskDefList(){
        return this.findList("queryTaskDefList");
    }

    @Override
    public int insertTaskDef(TaskDef taskDef){
        return this.insert("insert", taskDef);
    }

    @Override
    public TaskDef queryTaskDefById(Long id){
        return this.findOne("queryTaskDefById", id);
    }

}
