package com.meta.metadataserv.enums;

import org.apache.commons.lang3.StringUtils;

public enum TableEnum {
    T_METADATA_DB_TABLE("t_metadata_db_table"),
    T_METADATA_DB_COLUMN("t_metadata_db_column"),
    T_METADATA_DB_INDEX("t_metadata_db_index"),
    ;
    private String tableName;

    TableEnum(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTempTableName() {
        return tableName + "_temp";
    }


    public static TableEnum toEnum(String tableName) {
        return TableEnum.valueOf(StringUtils.upperCase(tableName));
    }
}
