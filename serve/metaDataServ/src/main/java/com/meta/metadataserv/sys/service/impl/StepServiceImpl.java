package com.meta.metadataserv.sys.service.impl;

import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.sys.dao.StepDao;
import com.meta.metadataserv.sys.service.IStepService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StepServiceImpl implements IStepService {
    @Resource
    private StepDao stepDao;

    /**
     * 获取步骤当前版本号
     * @return .
     */
    public Integer getCurStepVersion() {
        return stepDao.getCurStepVersion();
    }

    /**
     * 获取审核流程下拉 .
     * @param sortStr .
     * @return .
     */
    public List<SelectVo> getStepBySort(String sortStr, String stepVersion) {
        Integer version = StringUtils.isNotEmpty(stepVersion) ? Integer.valueOf(stepVersion) : getCurStepVersion();
        Integer sort = StringUtils.isNotEmpty(sortStr) ? Integer.valueOf(sortStr) : 1;
        List<Integer> sortList = new ArrayList<>();
        sortList.add(sort);
        sortList.add(++sort);
        return stepDao.getStepBySort(sortList, version);
    }

    /**
     * 根据流程id查询对应处理人 .
     * @param stepId .
     * @return .
     */
    public List<SelectVo> getTargetSelect(String stepId) {
        return stepDao.getTargetSelect(stepId);
    }

    /**
     * 查询该用户是否有当前步骤权限 .
     * @param stepId .
     * @param userId .
     * @return .
     */
    public boolean hasStepAble(Integer stepId, String userId) {
        List<Integer> nextStepList = stepDao.getNextStep(userId);
        if (nextStepList == null || !nextStepList.contains(stepId)) {
            throw new RuntimeException("无权限操作该流程");
        }
        return true;
    }

    /**
     * 获取最后步骤id
     * @return .
     */
    public Integer getFinalStep() {
        return stepDao.getFinalStep(getCurStepVersion());
    }
}
