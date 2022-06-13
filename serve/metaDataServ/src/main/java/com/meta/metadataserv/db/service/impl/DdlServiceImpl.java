package com.meta.metadataserv.db.service.impl;

import com.meta.metadataserv.db.dao.DdlDao;
import com.meta.metadataserv.db.service.IDdlService;
import com.meta.metadataserv.enums.TableEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DdlServiceImpl implements IDdlService {
    @Resource
    private DdlDao ddlDao;

    /**
     * 创建临时表 .
     * @param tableName .
     */
    public String createTempTable(String tableName) {
        TableEnum tableEnum = TableEnum.toEnum(tableName);
        return createTempTable(tableEnum);
    }

    /**
     * 创建临时表 .
     * @param tableEnum .
     */
    public String createTempTable(TableEnum tableEnum) {
        ddlDao.createTempTable(tableEnum.getTableName());
        return tableEnum.getTempTableName();
    }

    /**
     * 删除临时表，再重新创建 .
     * @param tableEnum
     * @return
     */
    public String createTempTableForce(TableEnum tableEnum) {
        dropTempTable(tableEnum.getTableName());
        return createTempTable(tableEnum);
    }

    /**
     * 删除表
     * @param tableName
     */
    public void dropTable(String tableName) {
        ddlDao.dropTable(tableName);
    }

    /**
     * 删除临时表 .
     * @param tableName
     */
    public void dropTempTable(String tableName) {
        TableEnum tableEnum = TableEnum.toEnum(tableName);
        dropTable(tableEnum.getTempTableName());
    }
}
