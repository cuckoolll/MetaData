package com.meta.metadataserv.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum CommonColumn {
    SORT("sort"),
    DATASTATUSID("datastatusid"),
    UPDATE_TIME("update_time"),
    UPDATE_BY("update_by"),
    CREATE_TIME("create_time"),
    CREATE_BY("create_by")
    ;

    private String columnName;

    public static List<String> getCommonColumn() {
        CommonColumn[] columns = CommonColumn.values();
        List<String> list = new ArrayList();
        for (CommonColumn column : columns) {
            list.add(column.getColumnName());
        }
        return list;
    }
}
