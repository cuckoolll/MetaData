package com.meta.metadataserv.sys.dao;


import com.meta.metadataserv.domain.common.SelectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StepDao {
    List<SelectVo> getStepBySort(@Param("sortList") List<Integer> sort, @Param("version") Integer version);

    Integer getCurStepVersion();

    List<SelectVo> getTargetSelect(@Param("stepId") String stepId);

    Integer getFinalStep(@Param("version") Integer version);

    List<Integer> getNextStep(@Param("userId") String userId);
}
