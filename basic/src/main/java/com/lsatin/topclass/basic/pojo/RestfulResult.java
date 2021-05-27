package com.lsatin.topclass.basic.pojo;

import com.lsatin.topclass.basic.enums.Response;
import com.lsatin.topclass.basic.enums.RestfulResponse;

/**
 * RESTFUL结果
 */
public class RestfulResult<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private RestfulResult() {
        this.code = RestfulResponse.NORMAL.getCode();
        this.message = RestfulResponse.NORMAL.getMessage();
    }

    private RestfulResult(T data) {
        this.data = data;
        this.code = RestfulResponse.SUCCESS.getCode();
        this.message = RestfulResponse.SUCCESS.getMessage();
    }

    private RestfulResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private RestfulResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RestfulResult<?> successful() {
        return new RestfulResult<>(RestfulResponse.SUCCESS.getCode(), RestfulResponse.SUCCESS.getMessage());
    }

    public static <T> RestfulResult<T> successful(T data) {
        return new RestfulResult<>(data);
    }

    public static RestfulResult<?> successful(int code, String message) {
        return new RestfulResult<>(code, message);
    }

    public static RestfulResult<?> failure(Response response) {
        return new RestfulResult<>(response.getCode(), response.getMessage());
    }
}
