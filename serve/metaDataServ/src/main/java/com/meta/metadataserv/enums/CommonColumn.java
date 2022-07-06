package com.meta.metadataserv.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum CommonColumn {
    SORT("sort", null),
    DATASTATUSID("datastatusid", null),
    UPDATE_TIME("update_time", "date_format(update_time, '%Y-%m-%d %h:%i:%s') update_time"),
    UPDATE_BY("update_by", null),
    CREATE_TIME("create_time", "date_format(create_time, '%Y-%m-%d %h:%i:%s') create_time"),
    CREATE_BY("create_by", null)
    ;

    private String columnName;
    private String format;

    public static List<String> getCommonColumn() {
        CommonColumn[] columns = CommonColumn.values();
        List<String> list = new ArrayList();
        for (CommonColumn column : columns) {
            list.add(column.getColumnName());
        }
        return list;
    }

    public static boolean contains(String columnName) {
        CommonColumn[] columns = CommonColumn.values();
        List<String> list = new ArrayList();
        for (CommonColumn column : columns) {
            if (columnName.equals(column.getColumnName())) {
                return true;
            }
        }
        return false;
    }

    public static String getFormat(String columnName) {
        CommonColumn[] columns = CommonColumn.values();
        List<String> list = new ArrayList();
        for (CommonColumn column : columns) {
            if (columnName.equals(column.getColumnName())) {
                if (StringUtils.isNotEmpty(column.getFormat())) {
                    return column.getFormat();
                }
            }
        }
        return columnName;
    }
}
