package com.rxkj.mapper;

import com.rxkj.entity.po.Command;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhanghaifeng
 * @description 针对表【command】的数据库操作Mapper
 * @createDate 2024-03-09 15:51:06
 * @Entity com.rxkj.entity.po.Command
 */
@Mapper
public interface CommandMapper extends BaseMapper<Command> {

}




