package com.meta.metadataserv.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
