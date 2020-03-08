package com.home.bootShiro.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guxc
 * @date 2020/3/1
 */
public class ResponseBO extends HashMap<String,Object> {

    private static final long serialVersionUID = 1L;

    public ResponseBO() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static ResponseBO error() {
        return error(1, "操作失败");
    }

    public static ResponseBO error(String msg) {
        return error(500, msg);
    }

    public static ResponseBO error(int code, String msg) {
        ResponseBO ResponseBO = new ResponseBO();
        ResponseBO.put("code", code);
        ResponseBO.put("msg", msg);
        return ResponseBO;
    }

    public static ResponseBO ok(String msg) {
        ResponseBO ResponseBO = new ResponseBO();
        ResponseBO.put("msg", msg);
        return ResponseBO;
    }

    public static ResponseBO ok(Map<String, Object> map) {
        ResponseBO ResponseBO = new ResponseBO();
        ResponseBO.putAll(map);
        return ResponseBO;
    }

    public static ResponseBO ok() {
        return new ResponseBO();
    }

    @Override
    public ResponseBO put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
