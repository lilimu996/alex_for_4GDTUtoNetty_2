package com.rxkj.service;

/**
* @author zhanghaifeng
* @description 针对表【operation】的数据库操作Service
* @createDate 2024-03-09 15:20:50
*/
public interface OperationService {

    void saveOperation(int deviceId, String userNumbers, int command);

}
