package com.rxkj.util;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yid
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public static JSONObject success() {
        return success(ResponseCode.SUCCESS_MSG, null);
    }

    public static JSONObject success(Object data) {
        return success(ResponseCode.SUCCESS_MSG, data);
    }

    public static JSONObject success(String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        jsonObject.put("data", JSONObject.toJSON(data));
        jsonObject.put("status", ResponseCode.STATUS_SUCCESS);
        return jsonObject;
    }

    public static JSONObject success(String msg, Object data, Object data1) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        jsonObject.put("data", JSONObject.toJSON(data));
        jsonObject.put("data1", JSONObject.toJSON(data1));
        jsonObject.put("status", ResponseCode.STATUS_SUCCESS);
        return jsonObject;
    }

    /**
     * 失败无需返回数据，因此不传入data
     *
     * @param status
     * @return
     */

    public static JSONObject failure(Integer status) {
        return failure(ResponseCode.getFailureMsg(status), "", status);
    }

    public static JSONObject failure(String msg, Integer status) {
        return failure(msg, "", status);
    }

    public static JSONObject failure(String msg, Object data, Integer status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        jsonObject.put("data", data);
        jsonObject.put("status", status);
        return jsonObject;
    }
}
