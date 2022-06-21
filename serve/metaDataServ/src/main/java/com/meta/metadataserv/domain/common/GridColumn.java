package com.meta.metadataserv.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "动态列",
        description = "动态列"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridColumn {
    @ApiModelProperty(value = "属性名")
    private String prop;

    @ApiModelProperty(value = "显示名")
    private String label;
}
