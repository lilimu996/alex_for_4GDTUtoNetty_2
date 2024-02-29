package com.rxkj.util;

import com.rxkj.message.MessageA;

/**
 * 自定义下发校验验证
 *
 * @author lilimu
 * @data 2023/5/26 10:48
 */

public interface ICheckFormat {

    /**
     * 验证是否符合规范
     *
     * @param msg
     * @return
     */
    Boolean checkFormat(MessageA msg);

    /**
     * 根据规范截取所需数据
     *
     * @param msg
     * @return
     */
    byte[] substring(byte[] msg);
}
