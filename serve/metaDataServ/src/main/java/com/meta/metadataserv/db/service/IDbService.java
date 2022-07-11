package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.Db;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.query.DbQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
public interface IDbService extends IService<Db> {
    /**
     * 查询数据库信息 .
     * @param cond
     * @return
     */
    Page<Db> getDb(DbQueryCond cond);

    /**
     * 新增
     * @param db
     * @return
     */
    int create(Db db);

    /**
     * 修改
     * @param db
     * @return
     */
    int update(Db db);

    /**
     * 删除
     * @param dbIds
     * @return
     */
    int delete(Set<String> dbIds);

    /**
     * 保存数据库信息 .
     * @param db
     */
    void saveDb(Db db);

    /**
     * 查询数据库名称下拉 .
     * @return
     */
    List<SelectVo> getSchemaSelect();

    /**
     * 删除数据库
     * @param projectId
     * @return
     */
    void delDb(String projectId);

    /**
     * 更新数据库时间 .
     * @param schema
     */
    void updateTime(String schema);
}
