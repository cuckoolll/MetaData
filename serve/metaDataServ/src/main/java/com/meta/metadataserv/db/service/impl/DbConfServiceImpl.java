package com.meta.metadataserv.db.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meta.metadataserv.db.dao.DbConfDao;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.db.service.IDbConfService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * @description  服务实现
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
@Service
public class DbConfServiceImpl extends ServiceImpl<DbConfDao, DbConf> implements IDbConfService {
    /**
     * 查询数据库配置信息 .
     * @param currentPage
     * @param size
     * @return
     */
    public Page<DbConf> getDbconf(Integer currentPage, Integer size) {
        currentPage = currentPage == null ? 1 : currentPage;
        size = size == null ? 20 : size;

        Page page = new Page<>(currentPage, size);
        Page<DbConf> result = getBaseMapper().getDbconf(page);
        return result;
    }

    /**
     * 新增
     * @param dbConf
     * @return
     */
    @Override
    public int create(DbConf dbConf) {
        if (StrUtil.isNotBlank(dbConf.getDbId())) {
            throw new RuntimeException("A new DbConf cannot already have an dbId");
        }
        Date currentDateTime = DateUtil.date();
        String currentUser = "sys";
        dbConf.setUpdateTime(currentDateTime);
        dbConf.setUpdateBy(currentUser);
        dbConf.setCreateTime(currentDateTime);
        dbConf.setCreateBy(currentUser);
        int result = getBaseMapper().insert(dbConf);
        return result;
    }

    /**
     * 修改
     * @param dbConf
     * @return
     */
    @Override
    public int update(DbConf dbConf) {
       if (!StrUtil.isNotBlank(dbConf.getDbId())) {
           throw new RuntimeException("A new DbConf not exist dbId");
       }
       Date currentDateTime = DateUtil.date();
        String currentUser = "sys";
       dbConf.setUpdateTime(currentDateTime);
       dbConf.setUpdateBy(currentUser);
       int result = getBaseMapper().updateById(dbConf);
       return result;
    }

    /**
     * 删除
     * @param dbIds
     * @return
     */
    @Override
    public int delete(Set<String> dbIds) {
        int result = getBaseMapper().deleteBatchIds(dbIds);
        return result;
    }
}
