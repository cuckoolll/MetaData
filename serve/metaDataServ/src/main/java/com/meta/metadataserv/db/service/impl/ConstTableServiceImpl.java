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
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.utils.FileUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
     * 创建常量表 .
     * @param constTable
     * @return
     */
    @Transactional
    public void createConstTable(ConstTable constTable) {
        TableQueryCond queryCond = new TableQueryCond();
        queryCond.setTableName(constTable.getTableName());
        queryCond.setSchema(constTable.getTableSchema());
        Page<DbTable> tableList = dbTableService.getDbTable(queryCond);
        if (tableList.getTotal() == 0) {
            throw new RuntimeException(constTable.getTableSchema() + "库中，表" + constTable.getTableName() + "不存在");
        }
        Page<ConstTable> constTablePage = getConstTable(queryCond);
        if (constTablePage.getTotal() > 0) {
            throw new RuntimeException("常量表" + constTable.getTableName() + "已创建");
        }

        String createSql = ddlService.getCreateTableSql(constTable.getTableName(), constTable.getTableSchema());
        try {
            ddlService.createTableByDynamicSql(createSql);
        } catch (Exception e) {
            throw new RuntimeException("建表sql执行错误");
        }

        getBaseMapper().create(constTable);
    }

    /**
     * 查询常量表 .
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
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    public List<GridColumn> getGridColumn(ColumnQueryCond queryCond) {
        return dbColumnService.getGridColumn(queryCond);
    }

    /**
     * 获取常量表列(无公共字段) .
     * @param queryCond
     * @return
     */
    public List<GridColumn> getGridColumnWithoutCommon(ColumnQueryCond queryCond) {
        return dbColumnService.getGridColumnWithoutCommon(queryCond);
    }

    /**
     * 导入常量表数据 .
     * @param file
     * @return
     */
    public void importData(MultipartFile file, String tableName) {
        String sql = FileUtil.readSqlFile(file);
        if (StringUtils.isEmpty(sql)) {
            throw new RuntimeException("sql文件为空");
        }

        sql = StringUtils.lowerCase(sql);
        if (sql.indexOf("insert into") == -1) {
            throw new RuntimeException("未找到插入语句");
        }

        int start = sql.indexOf("insert into") + "insert into".length();
        int end = sql.indexOf("(");
        String sqlTableName = sql.substring(start, end).trim();

        if (!StringUtils.lowerCase(tableName).equals(sqlTableName)) {
            throw new RuntimeException("sql文件表名不匹配");
        }

        try {
            sqlDao.execDynamicSql(sql);
        } catch (Exception e) {
            throw new RuntimeException("sql文件执行错误");
        }
    }

    /**
     * 查询常量表数据 .
     * @param queryCond
     * @return
     */
    public Page<Map> getData(CommonQueryCond queryCond) {
//        List<String> columnStrList = dbColumnService.getColumnNameList(queryCond.getTableName(), queryCond.getSchema());
        List<String> columnStrList = dbColumnService.getColumnNameListWithoutCommon(queryCond.getTableName(), queryCond.getSchema());
        String columnStr = CollectionUtil.join(columnStrList, ",");

        Page page = new Page(queryCond.getCurrentPage(), queryCond.getSize());
        Page<Map> result = getBaseMapper().getData(page, queryCond, columnStr);
        return result;
    }

    /**
     * 查询字段下拉列表 .
     * @param queryCond
     * @return
     */
    public List<SelectVo> getColumnQuerySelect(ColumnQueryCond queryCond) {
        return dbColumnService.getColumnQuerySelect(queryCond.getTableName(), queryCond.getSchema());
    }

    /**
     * 查询字段下拉列表(无通用字段) .
     * @param queryCond
     * @return
     */
    public List<SelectVo> getColumnQuerySelectWithoutCommon(ColumnQueryCond queryCond) {
        return dbColumnService.getColumnQuerySelectWithoutCommon(queryCond.getTableName(), queryCond.getSchema());
    }
}
