
package com.leo.job.repositories.support;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 * @author lvyuelei
 *         所有方法protected,全部定义到repository层(mybatis特定层)上来，
 */
@Named("genericRepository")
public class MybatisRepositorySupport<T> extends SqlSessionDaoSupport {

    private String className;

    @SuppressWarnings("unchecked")
    public MybatisRepositorySupport() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof Class) {
            this.className = ((Class<T>) type).getName();
        } else if (type instanceof ParameterizedType) {
            this.className = ((Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0]).getName();
        }
    }

    @Inject
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    protected Map<Object, Map<Object, Object>> findMap(String statement, String mapKey) {
        return getSqlSession().selectMap(this.className + "." + statement, mapKey);
    }

    protected Map<Object, Map<Object, Object>> findMap(String statement, Object parameter, String mapKey) {
        return getSqlSession().selectMap(this.className + "." + statement, parameter, mapKey);
    }

    protected <E> E findValue(String statement) {
        return getSqlSession().selectOne(this.className + "." + statement);
    }

    protected <E> E findValue(String statement, Object parameter) {
        return getSqlSession().selectOne(this.className + "." + statement, parameter);
    }

    protected <E> List<E> findValueList(String statement) {
        return getSqlSession().selectList(this.className + "." + statement);
    }

    protected <E> List<E> findValueList(String statement, Object parameter) {
        return getSqlSession().selectList(this.className + "." + statement, parameter);
    }

    protected T findOne(String statement) {
        return getSqlSession().selectOne(this.className + "." + statement);
    }

    protected T findOne(String statement, Object parameter) {
        return getSqlSession().selectOne(this.className + "." + statement, parameter);
    }

    protected List<T> findList(String statement) {
        return getSqlSession().selectList(this.className + "." + statement);
    }

    protected List<T> findList(String statement, Object parameter) {
        return getSqlSession().selectList(this.className + "." + statement, parameter);
    }

    protected int update(String statement) {
        int rows = getSqlSession().update(this.className + "." + statement);
        return rows;
    }

    protected int update(String statement, Object parameter) {
        int rows = getSqlSession().update(this.className + "." + statement, parameter);
        return rows;
    }

    protected int insert(String statement) {
        return getSqlSession().insert(this.className + "." + statement);
    }

    protected int insert(String statement, Object parameter) {
        return getSqlSession().update(this.className + "." + statement, parameter);
    }

    protected int delete(String statement) {
        return getSqlSession().delete(this.className + "." + statement);
    }

    protected int delete(String statement, Object parameter) {
        return getSqlSession().delete(this.className + "." + statement, parameter);
    }
}
