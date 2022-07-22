package com.meta.metadataserv.db.ctrl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.service.*;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.*;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.DbQueryCond;
import com.meta.metadataserv.domain.query.IndexQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private IDbColumnService dbColumnService;

    @Resource
    private IDbIndexService dbIndexService;

    @Resource
    private IDbService dbService;

    @ApiOperation("测试连接")
    @PostMapping("/testConnection")
    public RespResult testConnection(@Validated @RequestBody DbConf dbConf) {
        try {
            dbManagerService.testConnection(dbConf);
        } catch (Exception e) {
            return RespResult.error(e.getMessage(), e.getMessage());
        }
        return RespResult.ok();
    }

    @ApiOperation("同步数据库")
    @PostMapping("/syncMetaData")
    public RespResult syncMetaData(@RequestBody DbConf dbConf) {
        dbManagerService.syncMetaData(dbConf);
        return RespResult.ok();
    }

    @ApiOperation("通过条件查询表")
    @PostMapping("/getDbTable")
    public RespResult getDbTable(@RequestBody TableQueryCond queryCond) {
        Page<DbTable> result = dbTableService.getDbTable(queryCond);
        return RespResult.ok(result);
    }

    @ApiOperation("查询表字段")
    @PostMapping("/getDbColumn")
    public RespResult getDbColumn(@RequestBody ColumnQueryCond queryCond) {
        List<DbColumn> result = dbColumnService.getDbColumn(queryCond.getTableName(), queryCond.getSchema());
        return RespResult.ok(result);
    }

    @ApiOperation("查询索引")
    @PostMapping("/getDbIndex")
    public RespResult getDbIndex(@RequestBody IndexQueryCond queryCond) {
        List<DbIndex> result = dbIndexService.getDbIndex(queryCond.getTableName(), queryCond.getSchema());
        return RespResult.ok(result);
    }

    @ApiOperation("查询数据库信息")
    @PostMapping("/getDb")
    public RespResult getDb(@RequestBody DbQueryCond cond) {
        Page<Db> result = dbService.getDb(cond);
        return RespResult.ok(result);
    }

    @ApiOperation("保存数据库")
    @PostMapping("/saveDb")
    public RespResult saveDb(@RequestBody Db db) {
        try {
            dbService.saveDb(db);
        } catch (Exception e) {
            return RespResult.error(e.getMessage(), e.getMessage());
        }
        return RespResult.ok();
    }

    @ApiOperation("删除数据库")
    @PostMapping("/delDb")
    public RespResult delDb(@RequestParam String projectId) {
        dbService.delDb(projectId);
        return RespResult.ok();
    }

    @ApiOperation("导出创建表sql")
    @PostMapping("/exportTableSql")
    public RespResult exportTableSql(@RequestParam String tableId) {
        try {
            String result = dbManagerService.exportTableSql(tableId);
            return RespResult.ok(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage());
        }
    }
}
