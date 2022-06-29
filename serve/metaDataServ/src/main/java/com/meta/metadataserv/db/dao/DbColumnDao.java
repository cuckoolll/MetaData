package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
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
    List<DbColumn> getMysqlAllColumns(@Param("schema") String schema);

    /**
     * 查询mysql数据库字段 .
     * @param queryCond
     * @return
     */
    List<DbColumn> getMysqlColumns(@Param("queryCond") ColumnQueryCond queryCond);

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
    List<DbColumn> getDbColumn(@Param("tableName") String tableName, @Param("schema") String schema);

    /**
     * 查询字段名List .
     * @param tableName
     * @param schema
     * @return
     */
    List<String> getDbColumnNameList(@Param("tableName") String tableName, @Param("schema") String schema);

    /**
     * 查询字段下拉列表 .
     * @param tableName
     * @param schema
     * @return
     */
    List<SelectVo> getColumnQuerySelect(@Param("tableName") String tableName, @Param("schema") String schema);

    /**
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    List<GridColumn> getGridColumn(@Param("queryCond") ColumnQueryCond queryCond);

    /**
     * 根据条件，查询主键列名
     * @param cond
     * @return
     */
    DbColumn getPkColumnName(@Param("cond") ColumnQueryCond cond);
}
