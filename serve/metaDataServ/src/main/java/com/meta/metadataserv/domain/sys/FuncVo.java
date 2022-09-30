package com.meta.metadataserv.domain.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "功能对象",
        description = "功能对象"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncVo {
    @ApiModelProperty(value = "功能id")
    private String itemId;

    @ApiModelProperty(value = "功能名称")
    private String itemName;

    @ApiModelProperty(value = "菜单uri")
    private String menuUri;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "状态")
    private String datastatusid;
}
