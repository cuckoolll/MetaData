package com.meta.metadataserv.db.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.meta.metadataserv.domain.model.ColumnAlter;
import com.meta.metadataserv.domain.model.ColumnVo;
import com.meta.metadataserv.domain.model.IndexVo;
import com.meta.metadataserv.domain.model.OptionVo;
import com.meta.metadataserv.domain.query.OptionQueryCond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DbOptionDao {
    Integer getMaxOptId();

    void insertOption(@Param("option") OptionVo option);

    void insertColumnOption(@Param("columnList") List<ColumnVo> columnList, @Param("optId") Integer optId);

    void insertColumnAlter(@Param("alterList")List<ColumnAlter> list);

    void insertIndexOption(@Param("indexList")List<IndexVo> indexList, @Param("optId") Integer optId);

    OptionVo getOptionById(@Param("optId") Integer optId);

    void insertOptIntoDbTable(@Param("optId") Integer optId, @Param("optType") String optType);

    void insertOptIntoDbColumn(@Param("optId") Integer optId, @Param("optType") String optType);

    void insertOptIntoDbIndex(@Param("optId") Integer optId, @Param("optType") String optType);

    void updateOptIntoDbTable(@Param("optId") Integer optId, @Param("optType") String optType);

    void updateOptIntoDbColumn(@Param("optId") Integer optId, @Param("optType") String optType);

    void delDbTableFromOpt(@Param("optId") Integer optId, @Param("optType") String optType);

    void delDbColumnFromOpt(@Param("optId") Integer optId, @Param("optType") String optType);

    void delDbIndexFromOpt(@Param("optId") Integer optId, @Param("optType") String optType);

    Page<OptionVo> getOption(Page page, @Param("cond") OptionQueryCond cond, @Param("step") Integer step);

    List<ColumnVo> getColumnListById(@Param("optId") Integer optId);

    List<IndexVo> getIndexListById(@Param("optId") Integer optId);

    Integer getOptionExist(@Param("cond") OptionQueryCond cond, @Param("step") Integer step);

    Page<OptionVo> getOptionByTableAndSchema(Page page, @Param("cond") OptionQueryCond cond, @Param("step") Integer step);

    List<ColumnAlter> getColumnAlter(@Param("optId") String optId);

    void updateStep(@Param("optId") Integer optId,
                    @Param("step") Integer step,
                    @Param("stepVersion") Integer stepVersion,
                    @Param("userId") String userId);

    void finishStep(@Param("optId") Integer optId);
}
