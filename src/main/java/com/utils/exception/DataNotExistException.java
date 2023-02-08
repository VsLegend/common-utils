package com.utils.exception;

import org.springframework.util.ObjectUtils;

/**
 * @Author Wang Junwei
 * @Date 2022/3/25 11:03
 * @Description 业务异常
 */

public class DataNotExistException extends RuntimeException {

    private static final long serialVersionUID = 8449738842423044010L;

    private final Status status;

    public DataNotExistException() {
        super(Status.DATA_NOT_EXIST.msg);
        this.status = Status.DATA_NOT_EXIST;
    }

    public DataNotExistException(String message) {
        super(message);
        this.status = Status.SERVICE_ERROR;
    }

    public DataNotExistException(Status status) {
        this.status = status;
    }

    public DataNotExistException(Status status, String message) {
        super(message);
        this.status = status;
    }

    public short getCode() {
        return status.status;
    }

    public <T> Back<T> toBack() {
        return Back.failed(status, super.getLocalizedMessage());
    }

    @Override
    public String getMessage() {
        if (ObjectUtils.isEmpty(super.getMessage())) {
            return status.msg;
        }
        return super.getMessage();
    }
}
