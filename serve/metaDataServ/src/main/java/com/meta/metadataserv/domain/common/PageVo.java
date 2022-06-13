package com.meta.metadataserv.domain.common;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(
        value = "分页对象",
        description = "分页对象"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "条数")
    private Integer size;
}
