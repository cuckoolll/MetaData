package com.meta.metadataserv.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "下拉对象",
        description = "下拉对象"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "文本")
    private String text;
}
