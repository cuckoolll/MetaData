package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface DdlDao extends BaseMapper {
    /**
     * 创建临时表 .
     * @param tableName
     */
    void createTempTable(@Param("tableName") String tableName);

    /**
     * 删除表
     * @param tableName
     */
    void dropTable(@Param("tableName") String tableName);
}
