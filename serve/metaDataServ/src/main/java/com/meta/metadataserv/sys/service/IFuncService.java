package com.meta.metadataserv.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.sys.FuncVo;
import com.meta.metadataserv.domain.sys.RoleVo;

import java.util.List;

public interface IFuncService {
    /**
     * 查询功能 .
     * @param cond .
     * @return .
     */
    Page<FuncVo> getFuncs(CommonQueryCond cond);

    /**
     * 删除功能 .
     * @param itemId .
     */
    void delFunc(String itemId);

    /**
     * 创建/更新角色 .
     * @param func .
     */
    void saveFunc(FuncVo func);
}
