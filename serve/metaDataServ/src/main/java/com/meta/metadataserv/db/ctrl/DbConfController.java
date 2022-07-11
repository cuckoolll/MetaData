package com.meta.metadataserv.db.ctrl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.service.IDbService;
import com.meta.metadataserv.domain.common.PageVo;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.DbConf;
import com.meta.metadataserv.domain.query.DbConfQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.db.service.IDbConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description  前端控制器
 * @author duyu
 * @date 2022-05-25 11:41
 * @version V1.0
 **/
@Api(tags = {"数据库设置管理"})
@Slf4j
@RestController
@RequestMapping("/metaData/db/conf")
public class DbConfController {

	@Resource
	private IDbConfService baseDbConfService;

	@Resource
    private IDbService dbService;

//    @ApiOperation("查询数据库配置信息（GET）")
//    @GetMapping("/getDbconf")
//    public RespResult getDbconf(@RequestParam Integer currentPage, @RequestParam Integer size) {
//        Page<DbConf> result = baseDbConfService.getDbconf(currentPage, size);
//        return RespResult.ok(result);
//    }

    @ApiOperation("查询数据库配置信息（POST）")
    @PostMapping("/getDbconfByPost")
    public RespResult getDbconfByPost(@RequestBody DbConfQueryCond cond) {
        Page<DbConf> result = baseDbConfService.getDbconf(cond);
        return RespResult.ok(result);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody DbConf dbConf){
        baseDbConfService.create(dbConf);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody DbConf dbConf){
        baseDbConfService.update(dbConf);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> dbIds){
        baseDbConfService.delete(dbIds);
        return RespResult.ok();
    }

    @ApiOperation("保存")
    @PostMapping("/save")
    public RespResult save(@Validated @RequestBody DbConf dbConf) {
        if (StringUtils.isNotEmpty(dbConf.getDbId())) {
            update(dbConf);
        } else {
            create(dbConf);
        }
        return RespResult.ok();
    }

    /**
     * 查询数据库名称下拉 .
     * @return
     */
    @ApiOperation("查询数据库名称下拉")
    @PostMapping("/getSchemaSelect")
    public RespResult getSchemaSelect() {
        List<SelectVo> result = dbService.getSchemaSelect();
        return RespResult.ok(result);
    }
}
