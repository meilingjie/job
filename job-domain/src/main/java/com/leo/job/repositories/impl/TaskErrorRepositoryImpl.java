package com.leo.job.repositories.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.leo.job.repositories.TaskErrorRepository;
import com.leo.job.model.TaskError;
import com.leo.job.repositories.support.MybatisRepositorySupport;

/**
 * @author leo
 * @Description
 * @date 下午11:57
 */
@Named
public class TaskErrorRepositoryImpl extends MybatisRepositorySupport<TaskError> implements TaskErrorRepository {

    @Override
    public int insertTaskError(TaskError taskError){
        return this.insert("insert", taskError);
    }

    @Override
    public int updateTaskError(TaskError taskError) {
        return this.update("updateTaskError", taskError);
    }

    @Override
    public List<TaskError> queryTaskError(Short exceptionType) {
        return this.findList("queryTaskError", exceptionType);
    }

    @Override
    public List<TaskError> queryTaskErrorByDateAndType(Integer year, Integer month, Integer day, String taskCode, Short exceptionType) {
        Map<String, Object> map = new HashMap<>();

        map.put("year", year);
        map.put("month", month);
        map.put("day", day);
        map.put("taskCode", taskCode);
        map.put("exceptionType", exceptionType);

        return this.findList("queryTaskErrorByDateAndType", map);
    }

    @Override
    public List<TaskError> queryOtherTaskError(Short taskType) {
        return this.findList("queryOtherTaskError", taskType);
    }

    @Override
    public List<TaskError> queryErrorTaskDate(String taskCode) {
        Map<String, Object> map = new HashMap<>();

        map.put("taskCode", taskCode);

        return this.findList("queryErrorTaskDate", map);
    }
}
