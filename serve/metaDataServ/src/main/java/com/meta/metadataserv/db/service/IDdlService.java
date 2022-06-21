package com.meta.metadataserv.db.service;

import com.meta.metadataserv.enums.TableEnum;

public interface IDdlService {
    /**
     * 创建临时表 .
     * @param tableName .
     */
    String createTempTable(String tableName);

    /**
     * 创建临时表 .
     * @param tableEnum
     * @return
     */
    String createTempTable(TableEnum tableEnum);

    /**
     * 删除临时表，再重新创建 .
     * @param tableEnum
     * @return
     */
    String createTempTableForce(TableEnum tableEnum);

    /**
     * 删除表
     * @param tableName
     */
    void dropTable(String tableName);

    /**
     * 删除临时表 .
     * @param tableName
     */
    void dropTempTable(String tableName);

    /**
     * 通过动态sql创建表 .
     * @param sql
     */
    void createTableByDynamicSql(String sql);

    /**
     * 生成建表语句 .
     * @param tableName
     * @param schema
     * @return
     */
    String getCreateTableSql(String tableName, String schema);
}
