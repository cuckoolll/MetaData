package com.meta.metadataserv.db.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.ConstTableDao;
import com.meta.metadataserv.db.dao.SqlDao;
import com.meta.metadataserv.db.service.IConstTableService;
import com.meta.metadataserv.db.service.IDbColumnService;
import com.meta.metadataserv.db.service.IDbTableService;
import com.meta.metadataserv.db.service.IDdlService;
import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.ConstTable;
import com.meta.metadataserv.domain.model.ConstTableData;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.enums.CommonColumn;
import com.meta.metadataserv.utils.FileUtil;
import com.meta.metadataserv.utils.SqlUtil;
import com.meta.metadataserv.utils.UuidUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ConstTableServiceImpl extends ServiceImpl<ConstTableDao, ConstTable> implements IConstTableService {
    @Resource
    private IDdlService ddlService;

    @Resource
    private IDbTableService dbTableService;

    @Resource
    private IDbColumnService dbColumnService;

    @Resource
    private SqlDao sqlDao;

    /**
     * ??????????????? .
     * @param constTable
     * @return
     */
    @Transactional
    public void saveConstTable(ConstTable constTable) {
        if (StringUtils.isEmpty(constTable.getTableId())) {
            TableQueryCond queryCond = new TableQueryCond();
            queryCond.setTableName(constTable.getTableName());
            queryCond.setSchema(constTable.getTableSchema());
            Page<DbTable> tableList = dbTableService.getDbTable(queryCond);
            if (tableList.getTotal() == 0) {
                throw new RuntimeException(constTable.getTableSchema() + "????????????" + constTable.getTableName() + "?????????");
            }
            Page<ConstTable> constTablePage = getConstTable(queryCond);
            if (constTablePage.getTotal() > 0) {
                throw new RuntimeException("?????????" + constTable.getTableName() + "?????????");
            }

            String createSql = ddlService.getCreateTableSql(constTable.getTableName(), constTable.getTableSchema());
            try {
                ddlService.createTableByDynamicSql(createSql);
            } catch (Exception e) {
                throw new RuntimeException("??????sql????????????");
            }
            constTable.setTableId(UuidUtil.getUuid());
            getBaseMapper().create(constTable);
        } else {
            getBaseMapper().updateById(constTable);
        }
    }

    /**
     * ??????????????? .
     * @param queryCond
     * @return
     */
    public Page<ConstTable> getConstTable(TableQueryCond queryCond) {
        Integer currentPage = queryCond.getCurrentPage() == null ? 1 : queryCond.getCurrentPage();
        Integer size = queryCond.getSize() == null ? 20 : queryCond.getSize();
        Page page = new Page(currentPage, size);
        return getBaseMapper().getConstTable(page, queryCond);
    }

    /**
     * ?????????????????? .
     * @param queryCond
     * @return
     */
    public List<GridColumn> getGridColumn(ColumnQueryCond queryCond) {
        return dbColumnService.getGridColumn(queryCond);
    }

    /**
     * ??????????????????(???????????????) .
     * @param queryCond
     * @return
     */
    public List<GridColumn> getGridColumnWithoutCommon(ColumnQueryCond queryCond) {
        return dbColumnService.getGridColumnWithoutCommon(queryCond);
    }

    /**
     * ????????????????????? .
     * @param file
     * @return
     */
    public void importData(MultipartFile file, String tableName) {
        String sql = FileUtil.readSqlFile(file);
        if (StringUtils.isEmpty(sql)) {
            throw new RuntimeException("sql????????????");
        }

        sql = StringUtils.lowerCase(sql);
        if (sql.indexOf(SqlUtil.INSERT_INTO) == -1) {
            throw new RuntimeException("?????????????????????");
        }
        //??????????????????delete??????
        boolean isDelExist = (sql.indexOf(SqlUtil.DELETE_FROM) != -1);

        int start = sql.indexOf(SqlUtil.INSERT_INTO) + SqlUtil.INSERT_INTO.length();
        int end = sql.indexOf("(");
        String sqlTableName = sql.substring(start, end).trim();
        if (sqlTableName.indexOf(".") > 0) {
            sqlTableName = sqlTableName.split("\\.")[1];
            sql = sql.substring(0, start) + " " + sqlTableName + " " + sql.substring(end, sql.length() - 1);
        }

        if (!StringUtils.lowerCase(tableName).equals(sqlTableName)) {
            throw new RuntimeException("sql?????????????????????");
        }

        try {
            //????????????
            if (!isDelExist) {
                String delOriginSql = SqlUtil.DELETE_FROM + " " + sqlTableName;
                sqlDao.execDynamicSql(delOriginSql);
            }

            //????????????????????????
            sqlDao.execDynamicSql(sql);
        } catch (Exception e) {
            throw new RuntimeException("sql??????????????????");
        }
    }

    /* ????????????sql .
     * @author
     * @param	response
     * @param	cond
     * @return
     */
    public String exportDataWithSql(HttpServletResponse response, CommonQueryCond cond) {
        cond.setCurrentPage(1);
        cond.setSize(20000);
        List<Map> records = null;
        if (cond.getDataList() == null || cond.getDataList().isEmpty()) {
            Page<Map> page = getData(cond);
            records = page.getRecords();
        } else {
            records = cond.getDataList();
        }

        List<String> columnNameList = dbColumnService.getColumnNameList(cond.getTableName(), cond.getSchema());
        String column = CollectionUtil.join(columnNameList, ",");

        String insertSql = "INSERT INTO " + cond.getTableName() + "(" + column + ")" + "VALUES";

        StringBuilder sql = new StringBuilder();
        if (cond.getDataList() == null || cond.getDataList().isEmpty()) {
            if (StringUtils.isNotEmpty(cond.getColumnName()) && StringUtils.isNotEmpty(cond.getColumnValue())) {
                sql.append("DELETE FROM " + cond.getTableName() + " WHERE " + cond.getColumnName() + " LIKE '%" + cond.getColumnValue() + "%';\n");
            } else {
                sql.append("DELETE FROM " + cond.getTableName() + ";\n");
            }
        } else {
            ColumnQueryCond columnQueryCond = new ColumnQueryCond();
            columnQueryCond.setTableName(cond.getTableName());
            columnQueryCond.setSchema(cond.getSchema());
            DbColumn pkColumn = dbColumnService.getPkColumnName(columnQueryCond);
            if (pkColumn != null) {
                String delSql = "DELETE FROM " + cond.getTableName() + " WHERE " + pkColumn.getColumnName() + " IN ({0});\n";
                StringBuilder condSql = new StringBuilder();
                for (Map map : records) {
                    condSql.append("'" + map.get(pkColumn.getColumnName()) + "',");
                }
                condSql.deleteCharAt(condSql.length() - 1);
                sql.append(MessageFormat.format(delSql, new String[] {condSql.toString()}));
            }
        }

        sql.append(insertSql);

        int index = 0;
        for (Map map : records) {
            if (index % 10 == 0 && index > 0) {
                sql.deleteCharAt(sql.length() - 1);
                sql.append(";").append("\n").append(insertSql);
            }
            StringBuilder valuesSql = new StringBuilder("\n\t(");
            for (String key : columnNameList) {
                if (map.get(key) != null) {
                    valuesSql.append("'" + map.get(key) + "'" + ",");
                } else {
                    valuesSql.append("NULL" + ",");
                }
            }
            valuesSql.deleteCharAt(valuesSql.length() - 1);
            valuesSql.append("),");
            sql.append(valuesSql);
            if (index == records.size() - 1 ) {
                sql.deleteCharAt(sql.length() - 1);
                sql.append(";");
            }

            index++;
        }

        return sql.toString();
    }

    /**
     * ????????????????????? .
     * @param queryCond
     * @return
     */
    public Page<Map> getData(CommonQueryCond queryCond) {
        List<String> columnStrList = dbColumnService.getColumnNameList(queryCond.getTableName(), queryCond.getSchema());
//        List<String> columnStrList = dbColumnService.getColumnNameListWithoutCommon(queryCond.getTableName(), queryCond.getSchema());
        List<String> columnNameList = new ArrayList<>();
        for (String columnName : columnStrList) {
            if (CommonColumn.contains(columnName)) {
                columnName = CommonColumn.getFormat(columnName);
            }
            columnNameList.add(columnName);
        }
        String columnNameStr = CollectionUtil.join(columnNameList, ",");

        Page page = new Page(queryCond.getCurrentPage(), queryCond.getSize());
        Page<Map> result = getBaseMapper().getData(page, queryCond, columnNameStr);
        return result;
    }

    /**
     * ???????????????????????? .
     * @param queryCond
     * @return
     */
    public List<SelectVo> getColumnQuerySelect(ColumnQueryCond queryCond) {
        return dbColumnService.getColumnQuerySelect(queryCond.getTableName(), queryCond.getSchema());
    }

    /**
     * ????????????????????????(???????????????) .
     * @param queryCond
     * @return
     */
    public List<SelectVo> getColumnQuerySelectWithoutCommon(ColumnQueryCond queryCond) {
        return dbColumnService.getColumnQuerySelectWithoutCommon(queryCond.getTableName(), queryCond.getSchema());
    }

    /**
     * ????????????????????? .
     * @param constTableData
     */
    public void saveConstData(ConstTableData constTableData) {
        String tableName = constTableData.getTableName();
        String schema = constTableData.getSchema();
        List<Map> constDataList = constTableData.getConstDataList();

        //?????????????????????
        ColumnQueryCond cond = new ColumnQueryCond();
        cond.setSchema(schema);
        cond.setTableName(tableName);
        DbColumn pkColumn = dbColumnService.getPkColumnName(cond);
        if (pkColumn == null) {
            throw new RuntimeException("?????????" + schema + "???" + tableName + "???????????????");
        }

        //??????insert???update??????
        List<Map> updateList = new ArrayList<>();
        List<Map> insertList = new ArrayList<>();
        for (Map map : constDataList) {
            if (map.get(pkColumn.getColumnName()) == null) {
                if (!pkColumn.isAutoIncrease() && pkColumn.isVarcharType()) {
                    map.put(pkColumn.getColumnName(), UuidUtil.getUuid());
                }
                insertList.add(map);
            } else {
                updateList.add(map);
            }
        }

        //??????insert???update???sql
        List<String> insertSqlList = new ArrayList<>();
        List<String> updateSqlList = new ArrayList<>();
        List<String> columnNameList = dbColumnService.getColumnNameList(tableName, schema);
        String insertSql = "INSERT INTO " + tableName + " ({0}) VALUES ({1})";
        for (Map insertMap : insertList) {
            StringBuilder columnNameStr = new StringBuilder();
            StringBuilder valueSql = new StringBuilder();
            for (String columnName : columnNameList) {
                //???????????????????????????
                if (columnName.equals(pkColumn.getColumnName()) && pkColumn.isAutoIncrease()) {
                    continue;
                }
                columnNameStr.append(columnName + ",");

                String value = insertMap.get(columnName) == null ? "" : String.valueOf(insertMap.get(columnName));
                if (StringUtils.isNotEmpty(value)) {
                    valueSql.append("'" + value + "',");
                } else {
                    valueSql.append("NULL,");
                }
            }
            columnNameStr.deleteCharAt(columnNameStr.length() - 1);
            valueSql.deleteCharAt(valueSql.length() - 1);
            insertSqlList.add(MessageFormat.format(insertSql, new String[] {columnNameStr.toString(), valueSql.toString()}));
        }
        String updateSql = "UPDATE " + tableName + " SET {0} WHERE {1} ";
        for (Map updateMap : updateList) {
            String condSql = "";
            StringBuilder valueSql = new StringBuilder();
            for (String columnName : columnNameList) {
                if (columnName.equals(pkColumn.getColumnName())) {
                    condSql = columnName + "='" + (updateMap.get(columnName) == null ? "" : updateMap.get(columnName)) + "'";
                }
                String value = updateMap.get(columnName) == null ? null : String.valueOf(updateMap.get(columnName));
                if (StringUtils.isNotEmpty(value)) {
                    valueSql.append(columnName + "='" + value + "',");
                } else {
                    valueSql.append(columnName + "=NULL,");
                }
            }
            valueSql.deleteCharAt(valueSql.length() - 1);
            updateSqlList.add(MessageFormat.format(updateSql, new String[] {valueSql.toString(), condSql}));
        }

        //????????????insert???update
        try {
            if (!insertSqlList.isEmpty()) {
                sqlDao.execBatchDynamicSql(insertSqlList);
            }
            if (!updateSqlList.isEmpty()) {
                sqlDao.execBatchDynamicSql(updateSqlList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * ????????????????????? .
     * @param constTableData .
     */
    public void deleteConstData(ConstTableData constTableData) {
        //?????????????????????
        ColumnQueryCond cond = new ColumnQueryCond();
        cond.setSchema(constTableData.getSchema());
        cond.setTableName(constTableData.getTableName());
        DbColumn pkColumn = dbColumnService.getPkColumnName(cond);
        if (pkColumn == null) {
            throw new RuntimeException("?????????" + constTableData.getSchema() + "???" + constTableData.getTableName() + "???????????????");
        }

        List<String> delSqlList = new ArrayList<>();
        String delSql = "DELETE FROM " + constTableData.getTableName() + " WHERE {0}";
        List<Map> constDataList = constTableData.getConstDataList();
        for (Map map : constDataList) {
            String condSql = pkColumn.getColumnName() + "='" + map.get(pkColumn.getColumnName()) + "'";
            delSqlList.add(MessageFormat.format(delSql, new String[] {condSql}));
        }

        try {
            sqlDao.execBatchDynamicSql(delSqlList);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * ??????????????? .
     * @param tableId .
     */
    public void deleteConstTable(String tableId) {
        getBaseMapper().deleteById(tableId);
    }
}
