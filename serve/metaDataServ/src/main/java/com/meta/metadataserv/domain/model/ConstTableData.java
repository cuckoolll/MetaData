package com.meta.metadataserv.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@ApiModel(value="常量表数据", description="常量表数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstTableData {
    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("数据库名")
    private String schema;

    @ApiModelProperty("常量表数据List")
    private List<Map> constDataList;
}
