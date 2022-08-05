package com.meta.metadataserv.sys.ctrl;

import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.sys.service.IRoleService;
import com.meta.metadataserv.sys.service.IStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "系统管理")
@RestController
@RequestMapping("/metaData/sys")
@AllArgsConstructor
@Slf4j
public class SysController {
    @Resource
    private IRoleService roleService;

    @Resource
    private IStepService stepService;

    @ApiOperation("通过用户获取角色权限")
    @PostMapping("/getRoleRelByUserId")
    public RespResult getRoleRelByUserId(@RequestParam String userId){
        List<String> result = roleService.getRoleRelByUserId(userId);
        return RespResult.ok(result);
    }

    @ApiOperation("获取审核流程下拉")
    @PostMapping("/getStepBySort")
    public RespResult getStepBySort(@RequestParam String sort, @RequestParam String stepVersion){
        List<SelectVo> result = stepService.getStepBySort(sort, stepVersion);
        return RespResult.ok(result);
    }

    @ApiOperation("根据流程id查询对应处理人")
    @PostMapping("/getTargetSelect")
    public RespResult getTargetSelect(@RequestParam String stepId){
        List<SelectVo> result = stepService.getTargetSelect(stepId);
        return RespResult.ok(result);
    }

    @ApiOperation("查询该用户是否有当前步骤权限")
    @PostMapping("/hasStepAble")
    public RespResult hasStepAble(@RequestParam Integer stepId, @RequestParam String userId) {
        try {
            boolean result = stepService.hasStepAble(stepId, userId);
            return RespResult.ok(result);
        }  catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage(), e.getMessage());
        }
    }
}
