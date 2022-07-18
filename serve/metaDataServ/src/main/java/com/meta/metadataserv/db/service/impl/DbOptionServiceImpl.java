package com.meta.metadataserv.db.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.dao.DbOptionDao;
import com.meta.metadataserv.db.service.IDbColumnService;
import com.meta.metadataserv.db.service.IDbIndexService;
import com.meta.metadataserv.db.service.IDbOptionService;
import com.meta.metadataserv.db.service.IDbTableService;
import com.meta.metadataserv.domain.model.*;
import com.meta.metadataserv.domain.query.OptionQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.enums.OptType;
import com.meta.metadataserv.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DbOptionServiceImpl implements IDbOptionService {
    @Resource
    private DbOptionDao dbOptionDao;

    @Resource
    private IDbColumnService dbColumnService;

    @Resource
    private IDbTableService dbTableService;

    /**
     * 完成步骤数 .
     */
    private static final Integer FINISH_STEP = 1;
    private Integer getFinishStep() {
        return FINISH_STEP;
    }

    /**
     * 新增操作 .
     * @param option
     * @return
     */
    public Integer createOption(OptionVo option) {
        //判断当前表是否存在
        if (OptType.CREATE_TABLE.getType().equals(option.getOptType())) {
            TableQueryCond tableQueryCond = new TableQueryCond();
            tableQueryCond.setTableName(option.getTableName());
            tableQueryCond.setSchema(option.getTableSchema());
            Page<DbTable> tablePage = dbTableService.getDbTable(tableQueryCond);
            if (tablePage.getRecords() != null && tablePage.getRecords().size() > 0) {
                throw new RuntimeException(option.getTableSchema() + "库" + option.getTableName() + "表，已存在");
            }
        }

        Integer optId = dbOptionDao.getMaxOptId();
        if (optId == null) {
            optId = 0;
        }
        optId++;
        option.setOptId(optId);
        option.setStep(1);
        option.setTarget("sys");
        option.setUpdateBy("sys");
        option.setCreateBy("sys");
        if (StringUtils.isEmpty(option.getTableId())) {
            option.setTableId(UuidUtil.getUuid());
        }

        String tableOptType = option.getOptType();
        List<ColumnVo> newColumnList = option.getColumnList();
        List<DbColumn> oldColumnList = null;
        Map<String, DbColumn> oldColumnMap = new HashMap<>();
        //编辑表时，获取表原始字段List和索引List
        if (OptType.EDIT_TABLE.getType().equals(tableOptType)) {
            if (newColumnList != null && !newColumnList.isEmpty()) {
                oldColumnList = dbColumnService.getDbColumn(option.getTableName(), option.getTableSchema());
            }
            for (DbColumn column : oldColumnList) {
                oldColumnMap.put(column.getColumnId(), column);
            }
        }

        //保存操作记录
        dbOptionDao.insertOption(option);

        //获取字段最大sort
        Integer sort = dbColumnService.getMaxSortByTable(option.getTableName(), option.getTableSchema());
        if (sort == null) {
            sort = 0;
        }

        List<Map> columnChangeTypeSaveList = new ArrayList<>();
        List<ColumnVo> columnList = option.getColumnList();
        List<ColumnVo> columnSaveList = new ArrayList<>();
        for (ColumnVo vo : columnList) {
            if (StringUtils.isEmpty(vo.getOptType())) {
                continue;
            }
            ColumnVo saveVo = vo;

            //编辑列时，设置字段的变更类型
            if (OptType.EDIT_COLUMN.getType().equals(vo.getOptType())) {
                List<String> columnChangeTypeList = getColumnChangeTypeList(vo, oldColumnMap.get(vo.getColumnId()));
                if (columnChangeTypeList == null || columnChangeTypeList.isEmpty()) {
                    continue;
                }
                for (String changeType : columnChangeTypeList) {
                    Map<String,String> columnChangeTypeMap = new HashMap<>();
                    columnChangeTypeMap.put("columnId", vo.getColumnId());
                    columnChangeTypeMap.put("optType", changeType);
                    columnChangeTypeSaveList.add(columnChangeTypeMap);
                }
            }

            if (StringUtils.isEmpty(vo.getColumnId())) {
                saveVo.setColumnId(UuidUtil.getUuid());
            }
            saveVo.setOptId(optId);
            if (vo.isVarcharType()) {
                saveVo.setVarcharLength(vo.getColumnSize());
            } else {
                saveVo.setNumberLength(vo.getColumnSize());
            }
            if (OptType.ADD_COLUMN.getType().equals(vo.getOptType())) {
                saveVo.setSort(++sort);
            }
            if (vo.getIsNullable() == null) {
                saveVo.setIsNullable(1);
            }
            saveVo.setTableName(option.getTableName());
            saveVo.setTableSchema(option.getTableSchema());
            columnSaveList.add(saveVo);
        }
        if (columnSaveList != null && columnSaveList.size() > 0) {
            //保存字段操作记录
            dbOptionDao.insertColumnOption(columnSaveList, optId);

            if (columnChangeTypeSaveList.size() > 0) {
                //保存字段变更类型
                dbOptionDao.insertColumnAlter(columnChangeTypeSaveList);
            }
        }

        List<IndexVo> indexList = option.getIndexList();
        List<IndexVo> indexSaveList = new ArrayList<>();
        for (IndexVo vo : indexList) {
            IndexVo saveVo = vo;
            if (StringUtils.isEmpty(vo.getIndexId())) {
                saveVo.setIndexId(UuidUtil.getUuid());
            }
            saveVo.setOptId(optId);
            saveVo.setTableName(option.getTableName());
            saveVo.setTableSchema(option.getTableSchema());

            List<String> columnNameList = vo.getColumnNameList();
            for (String columnName : columnNameList) {
                saveVo.setColumnName(columnName);
                indexSaveList.add(saveVo);
            }
        }
        if (indexSaveList != null && indexSaveList.size() > 0) {
            //保存索引操作记录
            dbOptionDao.insertIndexOption(indexSaveList, optId);
        }

        return optId;
    }

    /**
     * 获取字段变更类型List .
     * @param newColumn
     * @param oldColumn
     * @return
     */
    private List<String> getColumnChangeTypeList(ColumnVo newColumn, DbColumn oldColumn) {
        List<String> columnChangeTypeList = new ArrayList<>();
        //字段名称变更
        if (!newColumn.getColumnName().equals(oldColumn.getColumnName())) {
            columnChangeTypeList.add(OptType.COLUMN_NAME_CHANGE.getType());
        }
        //字段类型变更
        if (!newColumn.getDataType().equals(oldColumn.getDataType())) {
            columnChangeTypeList.add(OptType.COLUMN_TYPE_CHANGE.getType());
        }
        //字段默认值变更
        if (!String.valueOf(newColumn.getColumnDefault()).equals(String.valueOf(oldColumn.getColumnDefault()))) {
            columnChangeTypeList.add(OptType.COLUMN_DEFAULT_CHANGE.getType());
        }
        //字段长度变更
        if (newColumn.getColumnSize() != oldColumn.getColumnSize()) {
            columnChangeTypeList.add(OptType.COLUMN_LENGTH_CHANGE.getType());
        }
        //字段精度变更
        if (newColumn.getNumberScale() != oldColumn.getNumberScale()) {
            columnChangeTypeList.add(OptType.COLUMN_SCALE_CHANGE.getType());
        }
        //字段备注变更
        if (!newColumn.getRemark().equals(oldColumn.getRemark())) {
            columnChangeTypeList.add(OptType.COLUMN_REMARK_CHANGE.getType());
        }
        return columnChangeTypeList;
    }

    /**
     * 完成操作记录
     * @param optId
     */
    public void finishOption(Integer optId) {
        //TODO 判断当前步骤是否完成，需要新建步骤配置表
        OptionVo option = dbOptionDao.getOptionById(optId);

        //将opt表数据更新至db表
        if (OptType.CREATE_TABLE.getType().equals(option.getOptType())) {
            //新建表
            dbOptionDao.insertOptIntoDbTable(optId, OptType.CREATE_TABLE.getType());
            dbOptionDao.insertOptIntoDbColumn(optId, OptType.ADD_COLUMN.getType());
            dbOptionDao.insertOptIntoDbIndex(optId, OptType.ADD_INDEX.getType());
        } else if (OptType.EDIT_TABLE.getType().equals(option.getOptType())) {
            //表结构更新
            dbOptionDao.insertOptIntoDbColumn(optId, OptType.ADD_COLUMN.getType());
            dbOptionDao.updateOptIntoDbColumn(optId, OptType.EDIT_COLUMN.getType());
            dbOptionDao.delDbColumnFromOpt(optId, OptType.DEL_COLUMN.getType());
            dbOptionDao.delDbIndexFromOpt(optId, OptType.DEL_INDEX.getType());
            dbOptionDao.insertOptIntoDbIndex(optId, OptType.ADD_INDEX.getType());
        } else if (OptType.DEL_TABLE.getType().equals(option.getOptType())) {
            //删除表
            dbOptionDao.delDbTableFromOpt(optId, OptType.DEL_TABLE.getType());
            dbOptionDao.delDbColumnFromOpt(optId, OptType.DEL_COLUMN.getType());
            dbOptionDao.delDbIndexFromOpt(optId, OptType.DEL_INDEX.getType());
        }

    }

    /**
     * 获取变更记录 .
     * @param cond
     * @return
     */
    public Page<OptionVo> getOption(OptionQueryCond cond) {
        Page page = new Page(cond.getCurrentPage(), cond.getSize());
        return dbOptionDao.getOption(page, cond, getFinishStep());
    }

    /**
     * 根据单号查询变更记录 .
     * @param optId
     * @return
     */
    public OptionVo getOptionById(String optId) {
        OptionVo vo = dbOptionDao.getOptionById(Integer.valueOf(optId));
        if (vo != null) {
            vo.setColumnList(dbOptionDao.getColumnListById(Integer.valueOf(optId)));
            vo.setIndexList(dbOptionDao.getIndexListById(Integer.valueOf(optId)));
        }
        return vo;
    }

    /**
     * 判断表是否有待处理的变更记录 .
     * @param cond
     * @return
     */
    public boolean isOptionInProc(OptionQueryCond cond) {
        Integer count = dbOptionDao.getOptionExist(cond, getFinishStep());
        if (count > 0) {
            return true;
        }
        return false;
    }
}
