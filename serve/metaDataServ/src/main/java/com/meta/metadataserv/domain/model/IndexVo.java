package com.meta.metadataserv.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="索引vo", description="索引vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexVo extends DbIndex {
    @ApiModelProperty("操作id")
    private Integer optId;

    @ApiModelProperty("操作类型")
    private String optType;

    @ApiModelProperty("字段名List")
    private List<String> columnNameList;
}
