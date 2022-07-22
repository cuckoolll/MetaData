package com.meta.metadataserv.db.ctrl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.service.IDbOptionService;
import com.meta.metadataserv.domain.model.OptionVo;
import com.meta.metadataserv.domain.query.OptionQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.enums.OptType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = {"数据库操作ctrl"})
@Slf4j
@RestController
@RequestMapping("/metaData/db/option")
public class DbOptionController {
    @Resource
    private IDbOptionService dbOptionService;

    @ApiOperation("新增操作")
    @PostMapping("/createOption")
    public RespResult createOption(@RequestBody OptionVo option) {
        Integer result = null;
        try {
            result = dbOptionService.createOption(option);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage(), e.getMessage());
        }
        return RespResult.ok(result);
    }

    @ApiOperation("完成操作记录")
    @PostMapping("/finishOption")
    public RespResult finishOption(@RequestParam Integer optId) {
        try {
            dbOptionService.finishOption(optId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage());
        }
        return RespResult.ok();
    }

    @ApiOperation("获取变更记录")
    @PostMapping("/getOption")
    public RespResult getOption(@RequestBody OptionQueryCond cond) {
        Page<OptionVo> result = dbOptionService.getOption(cond);
        return RespResult.ok(result);
    }

    @ApiOperation("获取表操作类型")
    @PostMapping("/getTableOptTypeSelect")
    public RespResult getTableOptTypeSelect() {
        return RespResult.ok(OptType.getTableOptTypeSelect());
    }

    @ApiOperation("获取字段操作类型")
    @PostMapping("/getColumnOptTypeSelect")
    public RespResult getColumnOptTypeSelect() {
        return RespResult.ok(OptType.getColumnOptTypeSelect());
    }

    @ApiOperation("获取索引操作类型")
    @PostMapping("/getIndexOptTypeSelect")
    public RespResult getIndexOptTypeSelect() {
        return RespResult.ok(OptType.getIndexOptTypeSelect());
    }

    @ApiOperation("根据单号查询变更记录")
    @PostMapping("/getOptionById")
    public RespResult getOptionById(@RequestParam String optId) {
        OptionVo result = dbOptionService.getOptionById(optId);
        return RespResult.ok(result);
    }

    @ApiOperation("判断表是否有待处理的变更记录")
    @PostMapping("/isOptionInProc")
    public RespResult isOptionInProc(@RequestBody OptionQueryCond cond) {
        boolean isOptionInProc = dbOptionService.isOptionInProc(cond);
        return RespResult.ok(isOptionInProc);
    }

    @ApiOperation("根据表和数据库查询变更记录")
    @PostMapping("/getOptionByTableAndSchema")
    public RespResult getOptionByTableAndSchema(@RequestBody OptionQueryCond cond) {
        Page<OptionVo> result = dbOptionService.getOptionByTableAndSchema(cond);
        return RespResult.ok(result);
    }

    @ApiOperation("变更记录sql导出")
    @PostMapping("/exportApplicationForm")
    public RespResult exportApplicationForm(@RequestParam String optId) {
        String result = dbOptionService.exportApplicationForm(optId);
        return RespResult.ok(result);
    }
}
