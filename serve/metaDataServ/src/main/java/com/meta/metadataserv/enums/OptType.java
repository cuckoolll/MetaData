package com.meta.metadataserv.enums;

import com.meta.metadataserv.domain.common.SelectVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum OptType {
    CREATE_TABLE("新建表", "create_table"),
    EDIT_TABLE("编辑表", "edit_table"),
    DEL_TABLE("删除表", "del_table"),
    ADD_COLUMN("新增字段", "add_column"),
    EDIT_COLUMN("编辑字段", "edit_column"),
    DEL_COLUMN("删除字段", "del_column"),
    ADD_INDEX("新增索引", "add_index"),
//    EDIT_INDEX("编辑索引", "edit_index"),
    DEL_INDEX("删除索引", "del_index"),

    //字段编辑类型
//    COLUMN_NAME_CHANGE("字段名称变更", "column_name_change"),
    COLUMN_TYPE_CHANGE("字段类型变更", "column_type_change"),
    COLUMN_DEFAULT_CHANGE("字段默认值变更", "column_default_change"),
    COLUMN_LENGTH_LARGER("字段长度增大", "column_length_larger"),
    COLUMN_LENGTH_SMALLER("字段长度缩小", "column_length_smaller"),
    COLUMN_SCALE_LARGER("字段精度增大", "column_scale_larger"),
    COLUMN_SCALE_SMALLER("字段精度缩小", "column_scale_smaller"),
    COLUMN_REMARK_CHANGE("字段备注变更", "column_remark_change"),

    //索引编辑类型
//    INDEX_NAME_CHANGE("索引名称变更", "index_name_change"),
//    INDEX_COLUMN_CHANGE("索引字段变更", "index_column_change"),
//    INDEX_UNIQUE_CHANGE("是否唯一索引变更", "index_unique_change"),
    ;

    private String name;
    private String type;

    public static List<SelectVo> getTableOptTypeSelect() {
        List<SelectVo> list = new ArrayList<>();
        list.add(new SelectVo(CREATE_TABLE.getType(), CREATE_TABLE.getName()));
        list.add(new SelectVo(EDIT_TABLE.getType(), EDIT_TABLE.getName()));
        list.add(new SelectVo(DEL_TABLE.getType(), DEL_TABLE.getName()));
        return list;
    }

    public static List<SelectVo> getColumnOptTypeSelect() {
        List<SelectVo> list = new ArrayList<>();
        list.add(new SelectVo(ADD_COLUMN.getType(), ADD_COLUMN.getName()));
        list.add(new SelectVo(EDIT_COLUMN.getType(), EDIT_COLUMN.getName()));
        list.add(new SelectVo(DEL_COLUMN.getType(), DEL_COLUMN.getName()));
        return list;
    }

    public static List<SelectVo> getIndexOptTypeSelect() {
        List<SelectVo> list = new ArrayList<>();
        list.add(new SelectVo(ADD_INDEX.getType(), ADD_INDEX.getName()));
        list.add(new SelectVo(DEL_INDEX.getType(), DEL_INDEX.getName()));
        return list;
    }
}
