package com.meta.metadataserv.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "通用查询条件",
        description = "通用查询条件"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonQueryCond {
    @ApiModelProperty(value = "所属库")
    private String schema;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "字段名")
    private String columnName;

    @ApiModelProperty(value = "字段值")
    private String columnValue;
}
