package com.meta.metadataserv.utils;

import com.meta.metadataserv.domain.model.*;
import com.meta.metadataserv.enums.OptType;
import com.meta.metadataserv.enums.Procedure;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SqlUtil {
    public static final String DELETE_FROM = "delete from";
    public static final String INSERT_INTO = "insert into";

    public static final String TEMPLATE =
            "DROP PROCEDURE IF EXISTS db_change;\n"
          + "DELIMITER //\n"
          + "-- 单号：{0}\n"
          + "-- 说明：{1}\n"
          + "-- 创建人：{2}\n"
          + "-- 创建时间：{3}\n"
          + "CREATE PROCEDURE db_change()\n"
          + "BEGIN\n"
          + "{4}\n"
          + "END\n"
          + "//\n"
          + "DELIMITER ;\n"
          + "CALL db_change();\n"
          + "DROP PROCEDURE IF EXISTS db_change;";

    /**
     * 根据模板生成sql .
     * @param sql .
     * @return .
     */
    public static String buildSqlTemplate(String sql, OptionVo option) {
        StringBuilder sqlBuilder = new StringBuilder("\t" + sql);
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        return MessageFormat.format(TEMPLATE, option.getOptId(), option.getDescription(), option.getCreateBy(),
                yyyyMMddHHmmss.format(option.getCreateTime()), sqlBuilder.toString().replaceAll("\n", "\n\t"));
    }

    /**
     * 生成建表sql
     * @param tableName 表名.
     * @param remark 表备注.
     * @param columnList 字段List.
     * @param indexList 索引List.
     * @return .
     */
    public static String buildCreateTableSql(String tableName, String remark, List<ColumnVo> columnList, List<IndexVo> indexList) {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (\n\t");

        //组装字段信息
        for (ColumnVo column : columnList) {
            sql.append(column.getColumnName()).append(" ")
                    .append(column.getDataType().toUpperCase());
            if (column.getColumnSizeByDataType() != null) {
                sql.append("(").append(column.getColumnSizeByDataType());
                if (column.getNumberScale() != null) {
                    sql.append(",").append(column.getNumberScale());
                }
                sql.append(")");
            }
            if (column.getIsPrimary() != null && column.getIsPrimary() == 1) {
                sql.append(" PRIMARY KEY");
            }
            if (column.getIsNullable() != null && column.getIsNullable() == 0) {
                sql.append(" NOT NULL");
            }
            if (StringUtils.isNotEmpty(column.getColumnDefault())) {
                sql.append(" DEFAULT ").append(column.getColumnDefault().toUpperCase());
            }
            if (column.isAutoIncrease()) {
                sql.append(" AUTO_INCREMENT");
            }
            sql.append(" COMMENT '").append(column.getRemark()).append("',");
            sql.append("\n\t");
        }

        //组装索引信息
        for (IndexVo index : indexList) {
            if (index.getIsUnique() == 1) {
                sql.append("UNIQUE INDEX ");
            } else {
                sql.append("INDEX ");
            }
            sql.append(index.getIndexName()).append("(");
            sql.append(index.getColumnName()).append("),");
            sql.append("\n\t");
        }

        sql.deleteCharAt(sql.length() - 3);
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") COMMENT '").append(remark).append("';\n");

        return sql.toString();
    }

    /**
     * 生成删除表sql .
     * @param tableName 表名 .
     * @param isBackup 是否以备份方式删除，0：否, 1：是
     * @return .
     */
    public static String buildDelTableSql(String tableName, int isBackup) {
        StringBuilder sql = new StringBuilder();

        if (1 == isBackup) {
            sql.append("CALL ").append(Procedure.buildSql(Procedure.PR_DEL_TABLE, new Object[]{tableName})).append(";");
        } else {
            sql.append("DROP TABLE IF EXISTS ").append(tableName).append(";");
        }

        return sql.toString() + "\n";
    }

    /**
     * 修改表备注 .
     * @param tableName 表名 .
     * @param remark 表备注 .
     * @return .
     */
    public static String buildUpdateTableRemarkSql(String tableName, String remark) {
        return "CALL " + Procedure.buildSql(Procedure.PR_EDIT_TABLE_REMARK, new Object[]{tableName, remark}) + ";\n";
    }

    /**
     * 新增字段 .
     * @param column .
     * @return .
     */
    public static String buildAddColumnSql(ColumnVo column) {
        return "CALL " + Procedure.buildSql(Procedure.PR_ADD_COLUMN, new Object[]{column.getTableName(), column.getColumnName(),
            column.getDataType(), column.getColumnSizeByDataType(), column.getNumberScale(), column.getColumnDefault(), column.getRemark()}) + ";\n";
    }

    /**
     * 删除字段 .
     * @param column .
     * @return .
     */
    public static String buildDelColumnSql(ColumnVo column) {
        return "CALL " + Procedure.buildSql(Procedure.PR_DEL_COLUMN, new Object[]{column.getTableName(), column.getColumnName()}) + ";\n";
    }

    /**
     * 新增或修改字段 .
     * @param column .
     * @return .
     */
    public static String buildAddOrEditColumnSql(ColumnVo column) {
        return "CALL " + Procedure.buildSql(Procedure.PR_ADD_OR_EDIT_COLUMN, new Object[]{column.getTableName(), column.getColumnName(),
                column.getDataType(), column.getColumnSizeByDataType(), column.getNumberScale(), column.getColumnDefault(), column.getRemark()}) + ";\n";
    }

    /**
     * 判断当前字段能否构建sql .
     * @return .
     */
    public static String isColumnBuildSql(ColumnVo column, List<String> optTypes) {
        if (optTypes.contains(OptType.COLUMN_TYPE_CHANGE.getType()) ||
            optTypes.contains(OptType.COLUMN_LENGTH_SMALLER.getType()) ||
            optTypes.contains(OptType.COLUMN_SCALE_SMALLER.getType())) {
            return "-- " + column.getColumnName() + "字段存在类型变更，长度或精度缩小，不支持自动构建脚本，请手工编写\n\n";
        }
        return null;
    }

    /**
     * 创建索引 .
     * @param index .
     * @return .
     */
    public static String buildAddIndexSql(IndexVo index) {
        return "CALL " + Procedure.buildSql(Procedure.PR_ADD_INDEX, new Object[]{index.getTableName(),
                index.getIndexName(), index.getColumnName(), index.getIsUnique()}) + ";\n";
    }

    /**
     * 删除索引 .
     * @param index .
     * @return .
     */
    public static String buildDelIndexSql(IndexVo index) {
        return "CALL " + Procedure.buildSql(Procedure.PR_DEL_INDEX, new Object[]{index.getTableName(), index.getIndexName()}) + ";\n";
    }
}
