package com.meta.metadataserv.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value="索引vo", description="索引vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexVo extends DbIndex {
    @ApiModelProperty("操作id")
    private Integer optId;

    @ApiModelProperty("操作类型")
    private String optType;

    @ApiModelProperty("字段名List")
    private List<String> columnNameList;

    public IndexVo(DbIndex index) {
        this.setColumnName(index.getColumnName());
        this.setTableSchema(index.getTableSchema());
        this.setTableName(index.getTableName());
        this.setIndexId(index.getIndexId());
        this.setCreateBy(index.getCreateBy());
        this.setCreateTime(index.getCreateTime());
        this.setDatastatusid(index.getDatastatusid());
        this.setIndexName(index.getIndexName());
        this.setIndexSchema(index.getIndexSchema());
        this.setIndexType(index.getIndexType());
        this.setIsUnique(index.getIsUnique());
        this.setSort(index.getSort());
        this.setUpdateBy(index.getUpdateBy());
        this.setUpdateTime(index.getUpdateTime());
    }

    public static List<IndexVo> listOf(List<DbIndex> indexList) {
        List<IndexVo> list = new ArrayList<>();
        for (DbIndex index : indexList) {
            list.add(new IndexVo(index));
        }
        return list;
    }
}
