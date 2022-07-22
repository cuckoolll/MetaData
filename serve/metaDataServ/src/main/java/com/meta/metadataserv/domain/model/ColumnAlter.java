package com.meta.metadataserv.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="字段变更类型", description="字段变更类型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnAlter {
    @ApiModelProperty("单号")
    private Integer optId;

    @ApiModelProperty("字段id")
    private String columnId;

    @ApiModelProperty("操作类型")
    private String optType;
}
