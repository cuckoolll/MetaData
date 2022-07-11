package com.meta.metadataserv.domain.query;

import com.meta.metadataserv.domain.common.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "数据库配置查询条件",
        description = "数据库配置查询条件"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbConfQueryCond extends PageVo  {
    @ApiModelProperty("数据库id")
    private String projectId;
}
