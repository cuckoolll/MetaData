package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.ConstTable;
import com.meta.metadataserv.domain.model.ConstTableData;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IConstTableService extends IService<ConstTable>  {
    /**
     * 创建常量表 .
     * @param constTable
     * @return
     */
    void createConstTable(ConstTable constTable);

    /**
     * 查询常量表 .
     * @param queryCond
     * @return
     */
    Page<ConstTable> getConstTable(TableQueryCond queryCond);

    /**
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    List<GridColumn> getGridColumn(ColumnQueryCond queryCond);

    /**
     * 获取常量表列(无公共字段) .
     * @param queryCond
     * @return
     */
    List<GridColumn> getGridColumnWithoutCommon(ColumnQueryCond queryCond);

    /**
     * 导入常量表数据 .
     * @param file
     * @return
     */
    void importData(MultipartFile file, String tableName);

    /* 导出数据sql .
     * @author
     * @param	response
     * @param	cond
     * @return
     */
    String exportDataWithSql(HttpServletResponse response, CommonQueryCond cond);

    /**
     * 查询常量表数据 .
     * @param queryCond
     * @return
     */
    Page<Map> getData(CommonQueryCond queryCond);

    /**
     * 查询字段下拉列表 .
     * @param queryCond
     * @return
     */
    List<SelectVo> getColumnQuerySelect(ColumnQueryCond queryCond);

    /**
     * 查询字段下拉列表(无通用字段) .
     * @param queryCond
     * @return
     */
    List<SelectVo> getColumnQuerySelectWithoutCommon(ColumnQueryCond queryCond);

    /**
     * 保存常量表数据 .
     * @param constTableData
     */
    void saveConstData(ConstTableData constTableData);

    /**
     * 删除常量表数据 .
     * @param constTableData .
     */
    void deleteConstData(ConstTableData constTableData);
}
