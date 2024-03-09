package com.rxkj.controller;

import com.rxkj.common.R;
import com.rxkj.service.impl.OperationServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Resource
    OperationServiceImpl operationService;

    @GetMapping("/getOperationList")
    R getOperationList(){
        return operationService.getOperationList();
    }


}
