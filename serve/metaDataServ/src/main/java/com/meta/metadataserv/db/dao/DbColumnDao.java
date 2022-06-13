package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DbColumnDao extends BaseMapper<DbColumn> {
    /**
     * 查询mysql数据库所有表字段 .
     * @param schema .
     * @return
     */
    List<DbColumn> getMysqlAllColumns(String schema);

    /**
     * 向column临时表插入数据 .
     * @param saveList
     */
    void insertIntoDbColumnTemp(@Param("saveList") List<DbColumn> saveList);

    /**
     * 将临时表数据插入至column表 .
     */
    void saveTempToDbColumn();

    /**
     * 查询表字段
     * @param tableName
     * @return
     */
    List<DbColumn> getDbColumn(@Param("tableName") String tableName);
}
