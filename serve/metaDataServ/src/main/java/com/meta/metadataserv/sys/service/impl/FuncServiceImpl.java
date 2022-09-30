package com.meta.metadataserv.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.sys.FuncVo;
import com.meta.metadataserv.domain.sys.RoleVo;
import com.meta.metadataserv.sys.dao.FuncDao;
import com.meta.metadataserv.sys.dao.RoleDao;
import com.meta.metadataserv.sys.service.IFuncService;
import com.meta.metadataserv.sys.service.IRoleService;
import com.meta.metadataserv.utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class FuncServiceImpl implements IFuncService {

    @Resource
    private FuncDao funcDao;

    /**
     * 查询角色 .
     * @param cond .
     * @return .
     */
    public Page<FuncVo> getFuncs(CommonQueryCond cond) {
        Page page = new Page(cond.getCurrentPage(), cond.getSize());
        return funcDao.getFuncs(page, cond);
    }

    /**
     * 删除功能 .
     * @param itemId .
     */
    public void delFunc(String itemId) {
        funcDao.delFunc(itemId);
    }

    /**
     * 创建/更新功能
     * @param func
     */
    public void saveFunc(FuncVo func) {
        String itemId = func.getItemId();

        if (StringUtils.isEmpty(itemId)) {
            try {
                func.setItemId(UuidUtil.getUuid());
                funcDao.insertFunc(func);
                return;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("创建功能异常");
            }
        }
        funcDao.updateFunc(func);
    }
}
