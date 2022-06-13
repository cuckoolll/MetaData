package com.meta.metadataserv.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.DbIndexDao;
import com.meta.metadataserv.db.service.IDbIndexService;
import com.meta.metadataserv.db.service.IDdlService;
import com.meta.metadataserv.domain.model.DbColumn;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.model.DbIndex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.meta.metadataserv.enums.TableEnum.T_METADATA_DB_INDEX;

@Service
public class DbIndexServiceImpl extends ServiceImpl<DbIndexDao, DbIndex> implements IDbIndexService {
    @Resource
    private IDdlService ddlService;

    /**
     * 通过元数据拉去索引，进行保存 .
     * @param dbConf
     */
    public void syncIndex(DbConf dbConf) {
        List<DbIndex> indexList = getBaseMapper().getMysqlAllIndex(dbConf.getDbSchema(), null);

        //增量增加，仅做Insert操作，已存在的索引不做更新处理
        String tempIndexTableName = ddlService.createTempTableForce(T_METADATA_DB_INDEX);
        getBaseMapper().insertIntoDbIndexTemp(indexList);
        getBaseMapper().saveTempToDbIndex();
        ddlService.dropTable(tempIndexTableName);
    }

    /**
     * 查询索引 .
     * @param tableName
     * @return
     */
    public List<DbIndex> getDbIndex(String tableName) {
        List<DbIndex> result = getBaseMapper().getDbIndex(tableName);
        return result;
    }
}
