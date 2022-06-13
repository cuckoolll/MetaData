package com.meta.metadataserv.db.ctrl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.service.IDbManagerService;
import com.meta.metadataserv.db.service.IDbTableService;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description  前端控制器
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
@Api(tags = {"数据库管理"})
@Slf4j
@RestController
@RequestMapping("/metaData/db/manager")
public class DbManagerController {
    @Resource
    private IDbManagerService dbManagerService;

    @Resource
    private IDbTableService dbTableService;

    /**
     * 测试连接 .
     * @param dbConf ,
     */
    @ApiOperation("测试连接")
    @PostMapping("/testConnection")
    public RespResult testConnection(@Validated @RequestBody DbConf dbConf) {
        dbManagerService.testConnection(dbConf);
        return RespResult.ok();
    }

    /**
     * 同步数据库 。
     * @param dbConf 。
     */
    @ApiOperation("同步数据库")
    @PostMapping("/syncMetaData")
    public RespResult syncMetaData(@RequestBody DbConf dbConf) {
        dbManagerService.syncMetaData(dbConf);
        return RespResult.ok();
    }


    /**
     * 通过条件查询表 .
     * @param queryCond
     * @return
     */
    @ApiOperation("通过条件查询表")
    @PostMapping("/getDbTable")
    public RespResult getDbTable(@RequestBody TableQueryCond queryCond) {
        Page<DbTable> result = dbTableService.getDbTable(queryCond);
        return RespResult.ok(result);
    }
}
