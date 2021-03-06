package com.meta.metadataserv.domain.query;

import com.meta.metadataserv.domain.common.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@ApiModel(
        value = "通用查询条件",
        description = "通用查询条件"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonQueryCond extends PageVo {
    @ApiModelProperty(value = "所属库")
    private String schema;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "字段名")
    private String columnName;

    @ApiModelProperty(value = "字段值")
    private String columnValue;

    @ApiModelProperty(value = "扩展数据")
    private List<Map> dataList;
}
