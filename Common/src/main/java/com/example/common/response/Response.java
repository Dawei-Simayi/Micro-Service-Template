package com.example.common.response;

import java.io.Serializable;

public class Response implements Serializable {
    private String code;
    private String msg;
    private Object data;

    public Response(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Response success(Object data) {
        return new Response("200", "success", data);
    }
}
