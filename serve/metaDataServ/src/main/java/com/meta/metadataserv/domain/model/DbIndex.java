package com.meta.metadataserv.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@ApiModel(value="数据库索引对象", description="数据库索引对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_metadata_db_index")
public class DbIndex {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "索引id")
    @TableField("index_id")
    private String indexId;

    @ApiModelProperty(value = "索引名")
    @TableField("index_name")
    private String indexName;

    @ApiModelProperty(value = "表名")
    @TableField("table_name")
    private String tableName;

    @ApiModelProperty(value = "是否唯一索引（0：否，1：是）")
    @TableField("is_unique")
    private Integer isUnique;

    @ApiModelProperty(value = "索引所属库")
    @TableField("index_schema")
    private String indexSchema;

    @ApiModelProperty(value = "字段名")
    @TableField("column_name")
    private String columnName;

    @ApiModelProperty(value = "索引类型")
    @TableField("index_type")
    private String indexType;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "数据状态(0:禁用、1:启用)")
    @TableField("datastatusid")
    private Integer datastatusid;

    @ApiModelProperty(value = "最后变更时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "最后操作员")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建操作员")
    @TableField("create_by")
    private String createBy;
}
