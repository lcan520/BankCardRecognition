package com.bankcardrecognition.server.result;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 17:02 2019/1/15
 * @Modified By:
 */
public class JsonResult {
    private boolean success = true;
    private int code = 200;
    private String message = "";
    private Map<Object, Object> data = new HashMap<>();

    public JsonResult() {
    }
    public static JsonResult success() {
       return new JsonResult(true,CodeMsg.SystemCodeMsg.SUCCESS.getCode(),CodeMsg.SystemCodeMsg.SUCCESS.getMsg());
    }
    public static JsonResult success(Map<Object, Object> data) {
        return new JsonResult(true,CodeMsg.SystemCodeMsg.SUCCESS.getCode(),CodeMsg.SystemCodeMsg.SUCCESS.getMsg(),data);
    }
    public static JsonResult failure(@NotNull CodeMsg codeMsg){
        return new JsonResult(codeMsg.getCode(),codeMsg.getMsg());
    }
    public JsonResult(boolean success) {
        this.success = success;
    }

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.success = false;
    }
    public JsonResult(boolean success,int code, String message) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
    public JsonResult(boolean success,int code, String message,Map<Object, Object> data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }
    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Object, Object> getData() {
        return this.data;
    }

    public void setData(Map<Object, Object> data) {
        this.data = data;
    }

    public void appendData(Object key, Object value) {
        this.data.put(key, value);
    }

    public void appendData(Map<?, ?> map) {
        this.data.putAll(map);
    }
}
