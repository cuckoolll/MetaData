package com.meta.metadataserv.db.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SqlDao {
    /**
     * 执行动态sql .
     * @param sql
     */
    public void execDynamicSql(@Param("sql") String sql);
}
