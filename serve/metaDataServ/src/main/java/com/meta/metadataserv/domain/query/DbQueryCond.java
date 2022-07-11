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
        value = "数据库查询条件",
        description = "数据库查询条件"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbQueryCond extends PageVo {
    @ApiModelProperty(value = "数据库名称")
    private String dbSchema;

    @ApiModelProperty(value = "数据库类型")
    private String dbType;
}
