package com.meta.metadataserv.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
@AllArgsConstructor
public enum Procedure {
    /**
     * 增加索引，创建唯一索引时会检查是否有重复数据，如果存在重复数据则需手工编写脚本处理
     *  V_TABLE_SCHEMA 数据库名
     *  V_TABLE_NAME VARCHAR 表名
     *  V_INDEX_NAME VARCHAR 索引名
     *  V_INDEX_COL VARCHAR 索引涉及字段
     *  V_INDEX_TYPE TINYINT 索引类型 0：普通索引，1：唯一索引
     */
    PR_ADD_INDEX("PR_ADD_INDEX", "PR_ADD_INDEX(\''{0}\'', \''{1}\'', \''{2}\'', \''{3}\'', {4})", "新增索引"),

    /**
     * 删除表，以备份方式删除表
     *  V_TABLE_SCHEMA 数据库名
     *  V_TABLE_NAME 表名
     */
    PR_DEL_TABLE("PR_DEL_TABLE", "PR_DEL_TABLE(\''{0}\'', \''{1}\'')", "删除表"),

    /**
     * 新增字段
     *  V_TABLE_SCHEMA 数据库名
     *  V_TABLE_NAME 表名
     *  V_COL_NAME 字段名
     *  V_COL_DATA_TYPE 数据类型
     *  V_COL_LENGTH 字段长度
     *  V_COL_ACCURACY 字段精度
     *  V_COL_DEFAULT 默认值
     *  V_COL_REMARK 备注
     */
    PR_ADD_COLUMN("PR_ADD_COLUMN", "PR_ADD_COLUMN(\''{0}\'', \''{1}\'', \''{2}\'', \''{3}\'', {4}, {5}, \''{6}\'', \''{7}\'')", "新增字段"),

    /**
     * 删除字段
     *  V_TABLE_SCHEMA 数据库名
     *  V_TABLE_NAME 表名
     *  V_COL_NAME 字段名
     */
    PR_DEL_COLUMN("PR_DEL_COLUMN", "PR_DEL_COLUMN(\''{0}\'', \''{1}\'', \''{2}\'')", "删除字段"),

    /**
     * 新增或编辑字段
     *  V_TABLE_SCHEMA 数据库名
     *  V_TABLE_NAME 表名
     *  V_COL_NAME 字段名
     *  V_COL_DATA_TYPE 数据类型
     *  V_COL_LENGTH 字段长度
     *  V_COL_ACCURACY 字段精度
     *  V_COL_DEFAULT 默认值
     *  V_COL_REMARK 备注
     */
    PR_ADD_OR_EDIT_COLUMN("PR_ADD_OR_EDIT_COLUMN", "PR_ADD_OR_EDIT_COLUMN(\''{0}\'', \''{1}\'', \''{2}\'', \''{3}\'', {4}, {5}, \''{6}\'', \''{7}\'')", "新增或编辑字段"),

    /**
     * 删除索引
     *  V_TABLE_SCHEMA 数据库名
     *  V_TABLE_NAME 表名
     *  V_INDEX_NAME 索引名
     */
    PR_DEL_INDEX("PR_DEL_INDEX", "PR_DEL_INDEX(\''{0}\'', \''{1}\'', \''{2}\'')", "删除索引"),

    /**
     * 修改表备注
     *  V_TABLE_SCHEMA 数据库名
     *  V_TABLE_NAME 表名
     * 	V_TABLE_REMARK 表备注
     */
    PR_EDIT_TABLE_REMARK("PR_EDIT_TABLE_REMARK", "PR_EDIT_TABLE_REMARK(\''{0}\'', \''{1}\'', \''{2}\'')", "修改表备注"),
    ;

    private String name;
    private String method;
    private String description;

    public static String buildSql(Procedure procedure, Object[] params) {
        return MessageFormat.format(procedure.getMethod(), params) ;
    }
}
