package com.lsatin.topclass.basic.enums;

/**
 * 服务响应
 * <p>标记一个响应体，由各个子类实现
 */
public interface Response {
    int getCode();
    String getMessage();
}
