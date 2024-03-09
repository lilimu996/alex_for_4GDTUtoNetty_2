package com.rxkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.entity.po.Command;
import com.rxkj.service.CommandService;
import com.rxkj.mapper.CommandMapper;
import org.springframework.stereotype.Service;

/**
* @author zhanghaifeng
* @description 针对表【command】的数据库操作Service实现
* @createDate 2024-03-09 15:51:06
*/
@Service
public class CommandServiceImpl extends ServiceImpl<CommandMapper, Command>
    implements CommandService{

}




