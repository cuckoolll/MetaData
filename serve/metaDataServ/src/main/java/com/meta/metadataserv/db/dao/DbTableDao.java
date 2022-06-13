package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.TableQueryCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DbTableDao extends BaseMapper<DbTable> {
    /**
     * 查询mysql数据库所有表 .
     * @param schema .
     * @return
     */
    List<DbTable> getMysqlAllTables(String schema);

    /**
     * 向table临时表插入数据 .
     * @param saveList
     */
    void insertIntoDbTableTemp(@Param("saveList") List<DbTable> saveList);

    /**
     * 将临时表数据插入至table表 .
     */
    void saveTempToDbTable();

    /**
     * 通过条件查询表 .
     * @param queryCond
     * @return
     */
    Page<DbTable> getDbTable(Page page, @Param("queryCond") TableQueryCond queryCond);
}
