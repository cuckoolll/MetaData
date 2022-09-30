package com.meta.metadataserv.domain.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "角色对象",
        description = "角色对象"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "状态")
    private String datastatusid;
}
