package com.rxkj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxkj.common.R;
import com.rxkj.entity.po.Operation;
import com.rxkj.entity.vo.OperationListVo;
import com.rxkj.mapper.OperationMapper;
import com.rxkj.service.OperationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    @Async
    public void saveOperation(int deviceId, String userNumbers, int command) {
        Date date = new Date();
        Operation operation = Operation.builder().operateId(command).time(date).deviceId(deviceId).userNumbers(userNumbers).build();

        operationMapper.insert(operation);
    }

    @Override
    public R getOperationList() {
        List<OperationListVo> operationList = operationMapper.getOperationList();
        return R.success(operationList);
    }
}




