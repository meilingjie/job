package com.leo.job.repositories.support;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author leo
 * @Description
 * @date 15/12/8 下午1:23
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }
}
