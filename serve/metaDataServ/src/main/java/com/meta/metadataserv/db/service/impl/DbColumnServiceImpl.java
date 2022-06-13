package com.meta.metadataserv.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.DbColumnDao;
import com.meta.metadataserv.db.service.IDbColumnService;
import com.meta.metadataserv.db.service.IDdlService;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.utils.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.meta.metadataserv.enums.TableEnum.T_METADATA_DB_COLUMN;

@Service
public class DbColumnServiceImpl extends ServiceImpl<DbColumnDao, DbColumn> implements IDbColumnService {
    @Resource
    private IDdlService ddlService;

    /**
     * 通过元数据拉取字段，进行保存 .
     */
    public void syncColumn(DbConf dbConf) {
        List<DbColumn> columnList = getBaseMapper().getMysqlAllColumns(dbConf.getDbSchema());

        //增量增加，仅做Insert操作，已存在的字段不做更新处理
        String tempColumnTableName = ddlService.createTempTableForce(T_METADATA_DB_COLUMN);
        getBaseMapper().insertIntoDbColumnTemp(columnList);
        getBaseMapper().saveTempToDbColumn();
        ddlService.dropTable(tempColumnTableName);
    }

}
