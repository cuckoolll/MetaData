package com.meta.metadataserv.domain.query;

import com.meta.metadataserv.domain.common.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(
        value = "操作记录查询条件",
        description = "操作记录查询条件"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionQueryCond extends PageVo {
    @ApiModelProperty(value = "操作类型")
    private String optType;

    @ApiModelProperty("数据库名称")
    private String tableSchema;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "当前处理人")
    private String target;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
