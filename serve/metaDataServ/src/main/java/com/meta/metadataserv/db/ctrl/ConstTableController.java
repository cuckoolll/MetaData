package com.meta.metadataserv.db.ctrl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.db.service.*;
import com.meta.metadataserv.domain.common.GridColumn;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.model.ConstTable;
import com.meta.metadataserv.domain.model.DbTable;
import com.meta.metadataserv.domain.query.ColumnQueryCond;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.query.TableQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 常量表接口 .
 */
@Api(tags = {"常量表接口"})
@Slf4j
@RestController
@RequestMapping("/metaData/db/constTable")
public class ConstTableController {
    @Resource
    private IConstTableService constTableService;

    /**
     * 创建常量表 .
     * @param constTable
     * @return
     */
    @ApiOperation("创建常量表")
    @PostMapping("/createConstTable")
    public RespResult createConstTable(@RequestBody ConstTable constTable) {
        try {
            constTableService.createConstTable(constTable);
        } catch (Exception e) {
            return RespResult.error(e.getMessage(), e.getMessage());
        }

        return RespResult.ok();
    }

    /**
     * 查询常量表 .
     * @param queryCond
     * @return
     */
    @ApiOperation("查询常量表")
    @PostMapping("/getConstTable")
    public RespResult getConstTable(@RequestBody TableQueryCond queryCond) {
        Page<ConstTable> result = constTableService.getConstTable(queryCond);
        return RespResult.ok(result);
    }

    /**
     * 获取常量表列.
     * @param queryCond
     * @return
     */
    @ApiOperation("获取常量表列")
    @PostMapping("/getGridColumn")
    public RespResult getGridColumn(@RequestBody ColumnQueryCond queryCond) {
        List<GridColumn> result = constTableService.getGridColumn(queryCond);
        return RespResult.ok(result);
    }

    /**
     * 获取常量表列.
     * @param queryCond
     * @return
     */
    @ApiOperation("获取常量表列（无公共字段）")
    @PostMapping("/getGridColumnWithoutCommon")
    public RespResult getGridColumnWithoutCommon(@RequestBody ColumnQueryCond queryCond) {
        List<GridColumn> result = constTableService.getGridColumnWithoutCommon(queryCond);
        return RespResult.ok(result);
    }

    /**
     * 导入常量表数据 .
     * @param file
     * @return
     */
    @ApiOperation("导入常量表数据")
    @PostMapping("/importData")
    public RespResult importData(@RequestPart("file") MultipartFile file, @RequestPart("tableName") String tableName) {
        try {
            constTableService.importData(file, tableName);
        } catch (Exception e) {
            return RespResult.error(e.getMessage(), e.getMessage());
        }

        return RespResult.ok();
    }

    /**
     * 查询常量表数据 .
     * @param queryCond
     * @return
     */
    @ApiOperation("查询常量表数据")
    @PostMapping("/getData")
    public RespResult getData(@RequestBody CommonQueryCond queryCond) {
        Page<Map> result = constTableService.getData(queryCond);
        return RespResult.ok(result);
    }

    /**
     * 查询字段下拉列表 .
     * @param queryCond
     * @return
     */
    @ApiOperation("查询字段下拉列表")
    @PostMapping("/getColumnQuerySelect")
    public RespResult getColumnQuerySelect(@RequestBody ColumnQueryCond queryCond) {
        List<SelectVo> result = constTableService.getColumnQuerySelect(queryCond);
        return RespResult.ok(result);
    }

    /**
     * 查询字段下拉列表(无通用字段) .
     * @param queryCond
     * @return
     */
    @ApiOperation("查询字段下拉列表(无通用字段)")
    @PostMapping("/getColumnQuerySelectWithoutCommon")
    public RespResult getColumnQuerySelectWithoutCommon(@RequestBody ColumnQueryCond queryCond) {
        List<SelectVo> result = constTableService.getColumnQuerySelectWithoutCommon(queryCond);
        return RespResult.ok(result);
    }
}