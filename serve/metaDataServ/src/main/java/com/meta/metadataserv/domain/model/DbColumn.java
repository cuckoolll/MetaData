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
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@ApiModel(value="数据库字段对象", description="数据库字段对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_metadata_db_column")
public class DbColumn {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段id")
    @TableField("column_id")
    private String columnId;

    @ApiModelProperty(value = "表名")
    @TableField("table_name")
    private String tableName;

    @ApiModelProperty(value = "所属库")
    @TableField("table_schema")
    private String tableSchema;

    @ApiModelProperty(value = "字段名")
    @TableField("column_name")
    private String columnName;

    @ApiModelProperty(value = "数据类型")
    @TableField("data_type")
    private String dataType;

    @ApiModelProperty(value = "默认值")
    @TableField("column_default")
    private String columnDefault;

    @ApiModelProperty(value = "是否主键（1：是，0或空：否）")
    @TableField("is_primary")
    private Integer isPrimary;

    @ApiModelProperty(value = "是否可空（0：不为空，1：可为空）")
    @TableField("is_nullable")
    private Integer isNullable;

    @ApiModelProperty(value = "字符串字段长度")
    @TableField("varchar_length")
    private Long varcharLength;

    @ApiModelProperty(value = "数值型字段长度")
    @TableField("number_length")
    private Long numberLength;

    @ApiModelProperty(value = "数值型字段精度")
    @TableField("number_scale")
    private Long numberScale;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "扩展方法")
    @TableField("extra")
    private String extra;

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

    /**
     * 是否自增序列 .
     * @return
     */
    public boolean isAutoIncrease() {
        if (StringUtils.isNotEmpty(getExtra()) && "auto_increment".equals(getExtra().toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 是否字符串型
     * @return
     */
    public boolean isVarcharType() {
        if ("varchar".equals(getDataType().toLowerCase()) || "text".equals(getDataType().toLowerCase())) {
            return true;
        }
        return false;
    }
}
