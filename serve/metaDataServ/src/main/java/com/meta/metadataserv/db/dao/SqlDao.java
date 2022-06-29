package com.meta.metadataserv.db.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SqlDao {
    /**
     * 执行动态sql .
     * @param sql
     */
    void execDynamicSql(@Param("sql") String sql);

    /**
     * 批量执行动态sql .
     * @param sql
     */
    void execBatchDynamicSql(@Param("sqlList") List<String> sql);

}
