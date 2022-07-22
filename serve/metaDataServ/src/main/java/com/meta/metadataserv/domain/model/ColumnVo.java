package com.meta.metadataserv.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value="字段vo", description="字段vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnVo extends DbColumn {
    @ApiModelProperty("操作id")
    private Integer optId;

    @ApiModelProperty("操作类型")
    private String optType;

    @ApiModelProperty("字段长度")
    private Long columnSize;

    public ColumnVo(DbColumn column) {
        this.setIsNullable(column.getIsNullable());
        this.setTableSchema(column.getTableSchema());
        this.setTableName(column.getTableName());
        this.setSort(column.getSort());
        this.setNumberLength(column.getNumberLength());
        this.setVarcharLength(column.getVarcharLength());
        this.setColumnId(column.getColumnId());
        this.setColumnDefault(column.getColumnDefault());
        this.setColumnName(column.getColumnName());
        this.setCreateBy(column.getCreateBy());
        this.setCreateTime(column.getCreateTime());
        this.setDatastatusid(column.getDatastatusid());
        this.setDataType(column.getDataType());
        this.setExtra(column.getExtra());
        this.setIsPrimary(column.getIsPrimary());
        this.setNumberScale(column.getNumberScale());
        this.setRemark(column.getRemark());
        this.setUpdateBy(column.getUpdateBy());
        this.setUpdateTime(column.getUpdateTime());
    }

    public static List<ColumnVo> listOf(List<DbColumn> columnList) {
        List<ColumnVo> list = new ArrayList<>();
        for (DbColumn column : columnList) {
            list.add(new ColumnVo(column));
        }
        return list;
    }

    public Long getColumnSizeByDataType() {
        return super.getColumnSize();
    }
}
