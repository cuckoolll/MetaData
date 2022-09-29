package com.meta.metadataserv.sys.ctrl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import com.meta.metadataserv.domain.sys.UserVo;
import com.meta.metadataserv.sys.service.IRoleService;
import com.meta.metadataserv.sys.service.IStepService;
import com.meta.metadataserv.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private IUserService userService;

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

    @ApiOperation("查询角色下拉")
    @PostMapping("/getRoleSelect")
    public RespResult getRoleSelect() {
        List<SelectVo> result = roleService.getRoleSelect();
        return RespResult.ok(result);
    }

    @ApiOperation("查询所有用户信息")
    @PostMapping("/getUsers")
    public RespResult getUsers(@RequestBody CommonQueryCond cond) {
        Page<UserVo> result = userService.getUsers(cond);
        return RespResult.ok(result);
    }

    @ApiOperation("更新用户启用停用状态")
    @PostMapping("/updateUserStatus")
    public RespResult updateUserStatus(@RequestParam String userId, @RequestParam Integer status) {
        try {
            userService.updateUserStatus(userId, status);
            return RespResult.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage(), e.getMessage());
        }
    }

    @ApiOperation("删除用户")
    @PostMapping("/delUser")
    public RespResult delUser(@RequestParam String userId) {
        try {
            userService.delUser(userId);
            return RespResult.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage(), e.getMessage());
        }
    }

    @ApiOperation("创建/更新用户")
    @PostMapping("/saveUser")
    public RespResult saveUser(@RequestBody UserVo user) {
        try {
            userService.saveUser(user);
            return RespResult.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage(), e.getMessage());
        }
    }

    @ApiOperation("重置密码")
    @PostMapping("/resetPassword")
    public RespResult resetPassword(String userId) {
        try {
            userService.resetPassword(userId);
            return RespResult.ok();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RespResult.error(e.getMessage(), e.getMessage());
        }
    }
}
