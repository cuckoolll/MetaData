package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.model.ConstTable;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import org.springframework.web.multipart.MultipartFile;

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
    List<GridColumn> getGridColumn(TableQueryCond queryCond);

    /**
     * 导入常量表数据 .
     * @param file
     * @return
     */
    void importData(MultipartFile file, String tableName);

    /**
     * 查询常量表数据 .
     * @param queryCond
     * @return
     */
    Page<Map> getData(CommonQueryCond queryCond);
}
