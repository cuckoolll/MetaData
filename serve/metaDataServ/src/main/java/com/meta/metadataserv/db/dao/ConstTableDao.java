package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.model.ConstTable;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ConstTableDao extends BaseMapper<ConstTable> {
    /**
     * 创建常量表 .
     * @param constTable
     */
    void create(@Param("constTable") ConstTable constTable);

    /**
     * 查询常量表 .
     * @param queryCond
     * @return
     */
    Page<ConstTable> getConstTable(Page page, @Param("queryCond") TableQueryCond queryCond);

    /**
     * 获取常量表列 .
     * @param queryCond
     * @return
     */
    List<GridColumn> getGridColumn(@Param("queryCond") TableQueryCond queryCond);

    /**
     * 查询常量表数据 .
     * @param queryCond
     * @return
     */
    Page<Map> getData(Page page, @Param("cond") CommonQueryCond queryCond, @Param("columns") String columns);
}
