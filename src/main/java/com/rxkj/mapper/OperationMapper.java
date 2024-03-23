package com.rxkj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rxkj.entity.po.Operation;
import com.rxkj.entity.vo.OperationListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author zhanghaifeng
* @description 针对表【operation】的数据库操作Mapper
* @createDate 2024-03-09 15:20:49
* @Entity com.rxkj.entity.po.Operation
*/

@Mapper
public interface OperationMapper extends BaseMapper<Operation> {

    List<OperationListVo> getOperationList();

}




