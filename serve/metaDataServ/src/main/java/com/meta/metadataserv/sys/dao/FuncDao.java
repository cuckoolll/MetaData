package com.meta.metadataserv.sys.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.common.SelectVo;
import com.meta.metadataserv.domain.query.CommonQueryCond;
import com.meta.metadataserv.domain.sys.FuncVo;
import com.meta.metadataserv.domain.sys.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FuncDao {

    Page<FuncVo> getFuncs(Page page, @Param("cond") CommonQueryCond cond);

    void delFunc(@Param("itemId") String itemId);

    void insertFunc(@Param("func") FuncVo func);

    void updateFunc(@Param("func") FuncVo func);
}
