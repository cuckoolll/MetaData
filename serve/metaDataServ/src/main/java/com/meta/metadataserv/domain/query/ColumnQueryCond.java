package com.meta.metadataserv.domain.query;

import com.meta.metadataserv.domain.common.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "字段查询条件",
        description = "字段查询条件"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnQueryCond extends PageVo {
    @ApiModelProperty(value = "所属库")
    private String schema;

    @ApiModelProperty(value = "表名")
    private String tableName;
}
