package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IDbColumnService extends IService<DbColumn>  {
    /**
     * 通过元数据拉取字段，进行保存 .
     */
    void syncColumn(DbConf dbConf);

    /**
     * 查询表字段
     * @param tableName
     * @return
     */
    List<DbColumn> getDbColumn(String tableName, String schema);

    /**
     * 从mysql字典表查询字段 .
     * @param queryCond
     * @return
     */
    List<DbColumn> getDbColumnFromOrigin(ColumnQueryCond queryCond);

    /**
     * 保存字段 .
     * @param columnList
     */
    void saveDbColumn(List<DbColumn> columnList);

    /**
     * 查询字段名List .
     * @param tableName
     * @param schema
     * @return
     */
    List<String> getColumnNameList(String tableName, String schema);

    /**
     * 查询字段名List(不包含通用字段) .
     * @param tableName
     * @param schema
     * @return
     */
    List<String> getColumnNameListWithoutCommon(String tableName, String schema);

    /**
     * 查询字段下拉列表 .
     * @param tableName
     * @param schema
     * @return
     */
    List<SelectVo> getColumnQuerySelect(String tableName, String schema);

    /**
     * 查询字段下拉列表 .
     * @param tableName
     * @param schema
     * @return
     */
    List<SelectVo> getColumnQuerySelectWithoutCommon(String tableName, String schema);

    /**
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    List<GridColumn> getGridColumn(ColumnQueryCond queryCond);

    /**
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    List<GridColumn> getGridColumnWithoutCommon(ColumnQueryCond queryCond);

    /**
     * 根据条件，查询主键列名
     * @param cond
     * @return
     */
    DbColumn getPkColumnName(ColumnQueryCond cond);

    /**
     * 根据表获取最大的字段排序 .
     * @param tableName
     * @param schema
     * @return
     */
    Integer getMaxSortByTable(String tableName, String schema);
}
