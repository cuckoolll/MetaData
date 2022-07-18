package com.meta.metadataserv.enums;

import com.meta.metadataserv.domain.common.SelectVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum DataType {
    TINY_INT("tinyint"),
    INT("int"),
    BIG_INT("bigint"),
    VARCHAR("varchar"),
    TIMESTAMP("timestamp"),
    DATETIME("datetime"),
    TEXT("text"),
    LONGTEXT("longtext"),
    DECIMAL("decimal"),
    ;

    private String name;

    public static List<SelectVo> getDataTypeSelect() {
        List<SelectVo> result = new ArrayList<>();
        DataType[] dataTypes = DataType.values();
        for (DataType dataType : dataTypes) {
            SelectVo vo = new SelectVo();
            vo.setId(dataType.getName());
            vo.setText(dataType.getName());
            result.add(vo);
        }
        return result;
    }
}
