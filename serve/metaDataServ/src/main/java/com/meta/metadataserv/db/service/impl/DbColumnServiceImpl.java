package com.meta.metadataserv.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.DbColumnDao;
import com.meta.metadataserv.db.service.IDbColumnService;
import com.meta.metadataserv.db.service.IDdlService;
import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.enums.CommonColumn;
import com.meta.metadataserv.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.meta.metadataserv.enums.TableEnum.T_METADATA_DB_COLUMN;

@Service
public class DbColumnServiceImpl extends ServiceImpl<DbColumnDao, DbColumn> implements IDbColumnService {
    @Resource
    private IDdlService ddlService;

    /**
     * 通过元数据拉取字段，进行保存 .
     */
    public void syncColumn(DbConf dbConf) {
        List<DbColumn> columnList = getBaseMapper().getMysqlAllColumns(dbConf.getDbSchema());
        saveDbColumn(columnList);
    }

    /**
     * 从mysql字典表查询字段 .
     * @param queryCond
     * @return
     */
    public List<DbColumn> getDbColumnFromOrigin(ColumnQueryCond queryCond) {
        return getBaseMapper().getMysqlColumns(queryCond);
    }

    /**
     * 保存字段 .
     * @param columnList
     */
    public void saveDbColumn(List<DbColumn> columnList) {
        //增量增加，仅做Insert操作，已存在的字段不做更新处理
        String tempColumnTableName = ddlService.createTempTableForce(T_METADATA_DB_COLUMN);
        getBaseMapper().insertIntoDbColumnTemp(columnList);
        getBaseMapper().saveTempToDbColumn();
        ddlService.dropTable(tempColumnTableName);
    }

    /**
     * 查询表字段
     * @param tableName
     * @return
     */
    public List<DbColumn> getDbColumn(String tableName, String schema) {
        List<DbColumn> result = getBaseMapper().getDbColumn(tableName, schema);
        return result;
    }

    /**
     * 查询字段名List .
     * @param tableName
     * @param schema
     * @return
     */
    public List<String> getColumnNameList(String tableName, String schema) {
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(schema)) {
            throw new RuntimeException(schema + "库" + tableName + "未查询到该表");
        }

        return getBaseMapper().getDbColumnNameList(tableName, schema);
    }

    /**
     * 查询字段名List(不包含通用字段) .
     * @param tableName
     * @param schema
     * @return
     */
    public List<String> getColumnNameListWithoutCommon(String tableName, String schema) {
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(schema)) {
            throw new RuntimeException(schema + "库" + tableName + "未查询到该表");
        }

        List<String> list = getBaseMapper().getDbColumnNameList(tableName, schema);
        List<String> commonColumnList = CommonColumn.getCommonColumn();
        int removeCount = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (removeCount >= commonColumnList.size()) {
                break;
            }

            if (commonColumnList.contains(list.get(i))) {
                list.remove(i);
                removeCount++;
            }
        }

        return list;
    }

    /**
     * 查询字段下拉列表 .
     * @param tableName
     * @param schema
     * @return
     */
    public List<SelectVo> getColumnQuerySelect(String tableName, String schema) {
        return getBaseMapper().getColumnQuerySelect(tableName, schema);
    }

    /**
     * 查询字段下拉列表 .
     * @param tableName
     * @param schema
     * @return
     */
    public List<SelectVo> getColumnQuerySelectWithoutCommon(String tableName, String schema) {
        List<SelectVo> selectList = getBaseMapper().getColumnQuerySelect(tableName, schema);
        List<String> commonColumnList = CommonColumn.getCommonColumn();
        int removeCount = 0;
        for (int i = selectList.size() - 1; i >= 0; i--) {
            if (removeCount >= commonColumnList.size()) {
                break;
            }

            if (commonColumnList.contains(selectList.get(i).getId())) {
                selectList.remove(i);
                removeCount++;
            }
        }
        return selectList;
    }

    /**
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    public List<GridColumn> getGridColumn(ColumnQueryCond queryCond) {
        return getBaseMapper().getGridColumn(queryCond);
    }

    /**
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    public List<GridColumn> getGridColumnWithoutCommon(ColumnQueryCond queryCond) {
        List<GridColumn> columnList = getGridColumn(queryCond);
        List<String> commonColumnList = CommonColumn.getCommonColumn();

        int removeCount = 0;
        for (int i = columnList.size() - 1; i >= 0; i--) {
            if (removeCount >= commonColumnList.size()) {
                break;
            }

            if (commonColumnList.contains(columnList.get(i).getProp())) {
                columnList.remove(i);
                removeCount++;
            }
        }
        return columnList;
    }

    /**
     * 根据条件，查询主键列名
     * @param cond
     * @return
     */
    public DbColumn getPkColumnName(ColumnQueryCond cond) {
        return getBaseMapper().getPkColumnName(cond);
    }

    /**
     * 根据表获取最大的字段排序 .
     * @param tableName
     * @param schema
     * @return
     */
    public Integer getMaxSortByTable(String tableName, String schema) {
        return getBaseMapper().getMaxSortByTable(tableName, schema);
    }
}
