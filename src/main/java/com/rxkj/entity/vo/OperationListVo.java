package com.rxkj.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationListVo {

    String userName;

    String commandStr;

    String time;

    Integer deviceId;

}
