package com.meta.metadataserv.db.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.model.OptionVo;
import com.meta.metadataserv.domain.query.OptionQueryCond;
import com.meta.metadataserv.domain.result.RespResult;
import org.springframework.web.bind.annotation.RequestParam;

public interface IDbOptionService {
    /**
     * 新增表 .
     * @param option
     * @return
     */
    Integer createOption(OptionVo option);

    /**
     * 完成操作记录
     * @param optId
     */
    void finishOption(Integer optId);

    /**
     * 获取变更记录 .
     * @param cond
     * @return
     */
    Page<OptionVo> getOption(OptionQueryCond cond);

    /**
     * 根据单号查询变更记录 .
     * @param optId
     * @return
     */
    OptionVo getOptionById(@RequestParam String optId);

    /**
     * 判断表是否有待处理的变更记录 .
     * @param cond
     * @return
     */
    boolean isOptionInProc(OptionQueryCond cond);
}
