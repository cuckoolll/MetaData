package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DbIndexDao extends BaseMapper<DbIndex> {
    /**
     * 向index临时表插入数据 .
     * @param saveList
     */
    void insertIntoDbIndexTemp(@Param("saveList") List<DbIndex> saveList);

    /**
     * 将临时表数据插入至index表 .
     */
    void saveTempToDbIndex();

    /**
     * 查询数据库索引信息 .
     * @param schema
     * @param tableName
     * @return
     */
    List<DbIndex> getMysqlAllIndex(@Param("schema") String schema, @Param("tableName") String tableName);

    /**
     * 查询索引 .
     * @param tableName
     * @return
     */
    List<DbIndex> getDbIndex(String tableName);
}
