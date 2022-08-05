package com.meta.metadataserv.db.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.dao.DbOptionDao;
import com.meta.metadataserv.db.service.IDbColumnService;
import com.meta.metadataserv.db.service.IDbOptionService;
import com.meta.metadataserv.db.service.IDbTableService;
import com.meta.metadataserv.domain.model.*;
import com.meta.metadataserv.domain.query.OptionQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.enums.OptType;
import com.meta.metadataserv.sys.service.IStepService;
import com.meta.metadataserv.utils.SqlUtil;
import com.meta.metadataserv.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class DbOptionServiceImpl implements IDbOptionService {
    @Resource
    private DbOptionDao dbOptionDao;

    @Resource
    private IDbColumnService dbColumnService;

    @Resource
    private IDbTableService dbTableService;

    @Resource
    private IStepService stepService;

    /**
     * 完成步骤数 .
     */
    private static final Integer FINISH_STEP = 1;
    private Integer getFinishStep() {
        return FINISH_STEP;
    }

    private synchronized Integer getMaxOptId() {
        return dbOptionDao.getMaxOptId();
    }

    /**
     * 新增操作 .
     * @param option .
     * @return .
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

        Integer optId = getMaxOptId();
        if (optId == null) {
            optId = 0;
        }
        optId++;
        option.setOptId(optId);
        option.setStep(option.getStep());
        option.setStepVersion(stepService.getCurStepVersion());
        if (StringUtils.isEmpty(option.getTableId())) {
            option.setTableId(UuidUtil.getUuid());
        }

        String tableOptType = option.getOptType();
        List<ColumnVo> newColumnList = option.getColumnList();
        List<DbColumn> oldColumnList = new ArrayList<>();
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

        List<ColumnAlter> columnChangeTypeSaveList = new ArrayList<>();
        List<ColumnVo> columnList = option.getColumnList();
        List<ColumnVo> columnSaveList = new ArrayList<>();
        for (ColumnVo vo : columnList) {
            if (OptType.DEL_TABLE.getType().equals(option.getOptType())) {
                vo.setOptType(OptType.DEL_COLUMN.getType());
            }

            if (StringUtils.isEmpty(vo.getOptType())) {
                continue;
            }
            ColumnVo saveVo = new ColumnVo();
            BeanUtils.copyProperties(vo, saveVo);

            //编辑列时，设置字段的变更类型
            if (OptType.EDIT_COLUMN.getType().equals(vo.getOptType())) {
                List<String> columnChangeTypeList = getColumnChangeTypeList(vo, oldColumnMap.get(vo.getColumnId()));
                if (columnChangeTypeList.isEmpty()) {
                    continue;
                }
                for (String changeType : columnChangeTypeList) {
                    ColumnAlter columnAlter = new ColumnAlter();
                    columnAlter.setColumnId(vo.getColumnId());
                    columnAlter.setOptType(changeType);
                    columnAlter.setOptId(optId);
                    columnChangeTypeSaveList.add(columnAlter);
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
        if (columnSaveList.size() > 0) {
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
            if (OptType.DEL_TABLE.getType().equals(option.getOptType())) {
                vo.setOptType(OptType.DEL_INDEX.getType());
            }

            if (StringUtils.isEmpty(vo.getOptType())) {
                continue;
            }

            IndexVo saveVo = vo;
            if (StringUtils.isEmpty(vo.getIndexId())) {
                saveVo.setIndexId(UuidUtil.getUuid());
            }
            saveVo.setOptId(optId);
            saveVo.setTableName(option.getTableName());
            saveVo.setTableSchema(option.getTableSchema());

            List<String> columnNameList = vo.getColumnNameList();
            for (String columnName : columnNameList) {
                IndexVo tempIndexVo = new IndexVo();
                BeanUtils.copyProperties(saveVo, tempIndexVo);
                tempIndexVo.setColumnName(columnName);
                indexSaveList.add(tempIndexVo);
            }
        }
        if (indexSaveList.size() > 0) {
            //保存索引操作记录
            dbOptionDao.insertIndexOption(indexSaveList, optId);
        }

        return optId;
    }

    /**
     * 获取字段变更类型List .
     * @param newColumn .
     * @param oldColumn .
     * @return .
     */
    private List<String> getColumnChangeTypeList(ColumnVo newColumn, DbColumn oldColumn) {
        List<String> columnChangeTypeList = new ArrayList<>();
        //字段名称变更
//        if (!newColumn.getColumnName().equals(oldColumn.getColumnName())) {
//            columnChangeTypeList.add(OptType.COLUMN_NAME_CHANGE.getType());
//        }
        //字段类型变更
        if (!newColumn.getDataType().equals(oldColumn.getDataType())) {
            columnChangeTypeList.add(OptType.COLUMN_TYPE_CHANGE.getType());
        }
        //字段默认值变更
        if (!String.valueOf(newColumn.getColumnDefault()).equals(String.valueOf(oldColumn.getColumnDefault()))) {
            columnChangeTypeList.add(OptType.COLUMN_DEFAULT_CHANGE.getType());
        }
        //字段长度增大
        long newColumnSize = newColumn.getColumnSize() == null ? 0 : newColumn.getColumnSize();
        long oldColumnSize = oldColumn.getColumnSize() == null ? 0 : oldColumn.getColumnSize();
        if (newColumnSize > oldColumnSize) {
            columnChangeTypeList.add(OptType.COLUMN_LENGTH_LARGER.getType());
        } else if (newColumnSize < oldColumnSize) {
            columnChangeTypeList.add(OptType.COLUMN_LENGTH_SMALLER.getType());
        }
        //字段精度变更
        long newNumberScale = newColumn.getNumberScale() == null ? 0 : newColumn.getNumberScale();
        long oldNumberScale = oldColumn.getNumberScale() == null ? 0 : oldColumn.getNumberScale();
        if (newNumberScale > oldNumberScale) {
            columnChangeTypeList.add(OptType.COLUMN_SCALE_LARGER.getType());
        } else if (newNumberScale < oldNumberScale) {
            columnChangeTypeList.add(OptType.COLUMN_SCALE_SMALLER.getType());
        }
        //字段备注变更
        if (!newColumn.getRemark().equals(oldColumn.getRemark())) {
            columnChangeTypeList.add(OptType.COLUMN_REMARK_CHANGE.getType());
        }
        return columnChangeTypeList;
    }

    /**
     * 完成操作记录
     * @param optId .
     */
    public void finishOption(Integer optId, Integer stepId, String userId) {
        //最后一步的步骤id
        Integer finalStep = stepService.getFinalStep();

        dbOptionDao.updateStep(optId, stepId, stepService.getCurStepVersion(), userId);
        //操作未处理完成
        if (stepId != finalStep) {
            return;
        }

        //完成操作记录
        dbOptionDao.finishStep(optId);

        //将opt表数据更新至db表
        OptionVo option = dbOptionDao.getOptionById(optId);
        if (OptType.CREATE_TABLE.getType().equals(option.getOptType())) {
            //新建表
            dbOptionDao.insertOptIntoDbTable(optId, OptType.CREATE_TABLE.getType());
            dbOptionDao.insertOptIntoDbColumn(optId, OptType.ADD_COLUMN.getType());
            dbOptionDao.insertOptIntoDbIndex(optId, OptType.ADD_INDEX.getType());
        } else if (OptType.EDIT_TABLE.getType().equals(option.getOptType())) {
            //表结构更新
            dbOptionDao.updateOptIntoDbTable(optId, OptType.EDIT_TABLE.getType());
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
     * @param cond .
     * @return .
     */
    public Page<OptionVo> getOption(OptionQueryCond cond) {
        Page page = new Page(cond.getCurrentPage(), cond.getSize());
        return dbOptionDao.getOption(page, cond, getFinishStep());
    }

    /**
     * 根据单号查询变更记录 .
     * @param optId .
     * @return .
     */
    public OptionVo getOptionById(String optId) {
        OptionVo vo = dbOptionDao.getOptionById(Integer.valueOf(optId));
        if (vo != null) {
            vo.setColumnList(dbOptionDao.getColumnListById(Integer.valueOf(optId)));

            List<IndexVo> indexList = dbOptionDao.getIndexListById(Integer.valueOf(optId));
            for (IndexVo indexVo : indexList) {
                String columnName = indexVo.getColumnName();
                List<String> columnNameList = new ArrayList<>();
                if (columnName.indexOf(",") > 0) {
                    columnNameList = Arrays.asList(columnName.split(","));
                } else {
                    columnNameList.add(columnName);
                }
                indexVo.setColumnNameList(columnNameList);
            }
            vo.setIndexList(indexList);
        }
        return vo;
    }

    /**
     * 判断表是否有待处理的变更记录 .
     * @param cond .
     * @return .
     */
    public boolean isOptionInProc(OptionQueryCond cond) {
        Integer count = dbOptionDao.getOptionExist(cond, getFinishStep());
        return count > 0;
    }

    /**
     * 根据表和数据库查询变更记录
     * @param cond .
     * @return .
     */
    public Page<OptionVo> getOptionByTableAndSchema(OptionQueryCond cond) {
        int currentPage = cond.getCurrentPage() == null ? 1 : cond.getCurrentPage();
        int size = cond.getSize() == null ? 100 : cond.getSize();
        Page page = new Page(currentPage, size);
        return dbOptionDao.getOptionByTableAndSchema(page, cond, getFinishStep());
    }

    /**
     * 变更记录sql导出 .
     * @param optId .
     * @return .
     */
    public String exportApplicationForm(String optId) {
        StringBuilder sql = new StringBuilder();

        OptionVo option = getOptionById(optId);

        String optType = option.getOptType();
        if (OptType.CREATE_TABLE.getType().equals(optType)) {       //创建表
            sql.append(SqlUtil.buildCreateTableSql(option.getTableName(), option.getRemark(),
                    option.getColumnList(), option.getIndexList()));
        } else if (OptType.DEL_TABLE.getType().equals(optType)) {   //删除表
            sql.append(SqlUtil.buildDelTableSql(option.getTableSchema(), option.getTableName(), 1));
        } else if (OptType.EDIT_TABLE.getType().equals(optType)) {  //编辑表
            //表备注修改
            sql.append(SqlUtil.buildUpdateTableRemarkSql(option.getTableSchema(), option.getTableName(), option.getRemark()));

            //字段变更
            List<ColumnVo> columnList = option.getColumnList();
            for (ColumnVo column : columnList) {
                if (OptType.ADD_COLUMN.getType().equals(column.getOptType())) {         //新增字段
                    sql.append(SqlUtil.buildAddColumnSql(column));
                } else if (OptType.DEL_COLUMN.getType().equals(column.getOptType())) {  //删除字段
                    sql.append(SqlUtil.buildDelColumnSql(column));
                } else if (OptType.EDIT_COLUMN.getType().equals(column.getOptType())) { //编辑字段
                    //查询字段变更情况
                    List<ColumnAlter> columnAlterList = dbOptionDao.getColumnAlter(optId);
                    Map<String, String> columnAlterMap = new HashMap<>();
                    for (ColumnAlter columnAlter : columnAlterList) {
                        columnAlterMap.put(columnAlter.getColumnId(), columnAlter.getOptType());
                    }

                    //根据字段变更情况生成sql
                    String columnId = column.getColumnId();
                    List<String> optTypes = Arrays.asList(columnAlterMap.get(columnId).split(","));
                    String hasSql = SqlUtil.isColumnBuildSql(column, optTypes);
                    if (StringUtils.isEmpty(hasSql)) {      //当前字段的变更类型支持导出sql
                        sql.append(SqlUtil.buildAddOrEditColumnSql(column));
                    } else {
                        sql.append(hasSql);
                    }
                }
            }

            //索引变更
            List<IndexVo> indexList = option.getIndexList();
            //遍历两次，将删除索引的sql放在新增索引前
            for (IndexVo index : indexList) {
                index.setTableSchema(option.getTableSchema());
                index.setTableName(option.getTableName());
                if (OptType.DEL_INDEX.getType().equals(index.getOptType())) {
                    sql.append(SqlUtil.buildDelIndexSql(index));
                }
            }
            for (IndexVo index : indexList) {
                index.setTableSchema(option.getTableSchema());
                index.setTableName(option.getTableName());
                if (OptType.ADD_INDEX.getType().equals(index.getOptType())) {
                    sql.append(SqlUtil.buildAddIndexSql(index));
                }
            }
        }

        return SqlUtil.buildSqlTemplate(sql.toString(), option);
    }


}
