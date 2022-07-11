package com.meta.metadataserv.db.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.DbConfDao;
import com.meta.metadataserv.db.dao.DbDao;
import com.meta.metadataserv.db.service.IDbConfService;
import com.meta.metadataserv.db.service.IDbService;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.Db;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.query.DbQueryCond;
import com.meta.metadataserv.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description  服务实现
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
@Service
public class DbServiceImpl extends ServiceImpl<DbDao, Db> implements IDbService {
    /**
     * 查询数据库信息 .
     * @param cond
     * @return
     */
    public Page<Db> getDb(DbQueryCond cond) {
        Page page = new Page<>(cond.getCurrentPage(), cond.getSize());
        Page<Db> result = getBaseMapper().getDb(page, cond);
        return result;
    }

    /**
     * 新增
     * @param db
     * @return
     */
    @Override
    public int create(Db db) {
        db.setProjectId(UuidUtil.getUuid());
        Date currentDateTime = DateUtil.date();
        String currentUser = "sys";
        db.setUpdateTime(currentDateTime);
        db.setUpdateBy(currentUser);
        db.setCreateTime(currentDateTime);
        db.setCreateBy(currentUser);
        int result = getBaseMapper().insert(db);
        return result;
    }

    /**
     * 修改
     * @param db
     * @return
     */
    @Override
    public int update(Db db) {
       Date currentDateTime = DateUtil.date();
        String currentUser = "sys";
        db.setUpdateTime(currentDateTime);
        db.setUpdateBy(currentUser);
       int result = getBaseMapper().updateById(db);
       return result;
    }

    /**
     * 删除
     * @param projectIds
     * @return
     */
    @Override
    public int delete(Set<String> projectIds) {
        int result = getBaseMapper().deleteBatchIds(projectIds);
        return result;
    }

    /**
     * 保存数据库信息 .
     * @param db
     */
    public void saveDb(Db db) {
        if (StringUtils.isEmpty(db.getProjectId())) {
            if (isDbExist(db.getDbSchema())) {
                throw new RuntimeException("数据库" + db.getDbSchema() + "已存在");
            }
            create(db);
        } else {
            update(db);
        }
    }

    /**
     * 查询数据库是否存在 .
     * @param dbSchema
     * @return
     */
    public boolean isDbExist(String dbSchema) {
        Integer count = getBaseMapper().getDbExist(dbSchema);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询数据库名称下拉 .
     * @return
     */
    public List<SelectVo> getSchemaSelect() {
        List<SelectVo> selectList = getBaseMapper().getSchemaSelect();
        return selectList;
    }

    /**
     * 删除数据库
     * @param projectId
     * @return
     */
    public void delDb(String projectId) {
        getBaseMapper().deleteById(projectId);
    }
}
