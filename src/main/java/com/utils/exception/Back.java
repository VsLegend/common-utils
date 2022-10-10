package com.utils.exception;


import lombok.Data;

import java.io.Serializable;

/**
 * @Author Wang Junwei
 * @Date 2022/3/24 15:45
 * @Description 公共返回结果构造类
 */

@Data
public class Back<T> implements Serializable {

    private static final long serialVersionUID = 6344132702118934448L;

    private short status;
    private T data;
    private String message;

    private Back() {
        this.status = Status.OK.getStatus();
    }

    private Back(T data) {
        this.status = Status.OK.getStatus();
        this.data = data;
    }

    public Back(Status status) {
        this.status = status.getStatus();
        this.message = status.getMsg();
    }

    public Back(Status status, String message) {
        this.status = status.getStatus();
        this.message = message;
    }

    public Back(Status status, String message, T data) {
        this.status = status.getStatus();
        this.message = message;
        this.data = data;
    }

    public static <U> Back<U> success() {
        return new Back<>();
    }

    public static <U> Back<U> success(U data) {
        return new Back<>(data);
    }

    public static <U> Back<U> exception(Exception e) {
        if (e instanceof BusinessException) {
            return ((BusinessException) e).toBack();
        } else {
            return new Back<>(Status.SERVICE_ERROR, null);
        }
    }

    public static <U> Back<U> failed(Status status) {
        return new Back<>(status);

    }

    public static <U> Back<U> failed(Status status, String message) {
        return new Back<>(status, message);

    }

    public static <U> Back<U> failed(Status status, String message, U data) {
        return new Back<>(status, message, data);

    }

    public static <U> Back<U> ok(String msg){
        return new Back<>(Status.OK,msg);
    }
}
