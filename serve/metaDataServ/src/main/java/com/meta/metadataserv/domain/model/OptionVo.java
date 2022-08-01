package com.meta.metadataserv.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel(value="表vo", description="表vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionVo {
    @ApiModelProperty("单号")
    private Integer optId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("操作类型")
    private String optType;

    @ApiModelProperty("表id")
    private String tableId;

    @ApiModelProperty("数据库名称")
    private String tableSchema;

    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("表备注")
    private String remark;

    @ApiModelProperty("说明")
    private String description;

    @ApiModelProperty("处理人id")
    private String target;

    @ApiModelProperty("处理人")
    private String targetName;

    @ApiModelProperty("字段信息")
    private List<ColumnVo> columnList;

    @ApiModelProperty("索引信息")
    private List<IndexVo> indexList;

    @ApiModelProperty("步骤数")
    private Integer step;

    @ApiModelProperty("最后操作人id")
    private String updateBy;

    @ApiModelProperty("最后操作人")
    private String updateByName;

    @ApiModelProperty(value = "最后变更时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;

    @ApiModelProperty("创建人id")
    private String createBy;

    @ApiModelProperty("创建人")
    private String createByName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;
}
