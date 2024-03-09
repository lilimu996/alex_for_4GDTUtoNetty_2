package com.rxkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.entity.po.Operation;
import com.rxkj.service.OperationService;
import com.rxkj.mapper.OperationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhanghaifeng
 * @description 针对表【operation】的数据库操作Service实现
 * @createDate 2024-03-09 15:20:49
 */
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {

    @Resource
    OperationMapper operationMapper;

    @Override
    public void saveOperation(int deviceId, String userNumbers, int command) {
        Date date = new Date();
        Operation operation = Operation.builder().operateId(command).time(date).deviceId(deviceId).userNumbers(userNumbers).build();

        operationMapper.insert(operation);
    }
}




