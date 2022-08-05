package com.meta.metadataserv.sys.service;

import com.meta.metadataserv.domain.common.SelectVo;

import java.util.List;

public interface IStepService {
    /**
     * 获取步骤当前版本号
     * @return
     */
    Integer getCurStepVersion();

    /**
     * 获取审核流程下拉 .
     * @param sortStr
     * @return
     */
    List<SelectVo> getStepBySort(String sortStr, String stepVersion);

    /**
     * 根据流程id查询对应处理人 .
     * @param stepId
     * @return
     */
    List<SelectVo> getTargetSelect(String stepId);

    /**
     * 获取最后步骤id
     * @return
     */
    Integer getFinalStep();

    /**
     * 查询该用户是否有当前步骤权限 .
     * @param stepId .
     * @param userId .
     * @return .
     */
    boolean hasStepAble(Integer stepId, String userId);
}
