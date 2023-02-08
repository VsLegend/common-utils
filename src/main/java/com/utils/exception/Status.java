package com.utils.exception;

import org.springframework.util.ObjectUtils;

/**
 * @Author Wang Junwei
 * @Date 2022/6/28 11:36
 * @Description 服务器返回状态码
 */
public enum Status {
    /**
     * 请求成功
     */
    OK(0, "OK"),

    /**
     * 未知的服务器异常
     */
    SERVICE_ERROR(9, "服务器运行错误，请联系管理员处理"),


    DATA_NOT_EXIST(99, "查询数据不存在"),




    /**
     * 文件处理异常
     */
    FILE_ERROR(1000, "文件处理异常，请联系工程师处理"),
    FILE_NAME_ENCODE_ERROR(1001, "文件名编码异常，请联系工程师处理"),
    FILE_DOWNLOAD_ERROR(1010, "文件下载异常，请稍后再试"),
    FILE_NOT_FOUND(1020, "文件不存在，请联系工程师处理"),
    FILE_STREAM_IS_EMPTY(1021, "文件流为空，请联系工程师处理"),
    FILE_DATA_IS_EMPTY(1022, "导入失败，请仔细检查核对Excel中，确保文件中表头需和模板中表头一致、且确保文件中数据不能为空"),

    ;

    public final short status;

    public final String msg;

    Status(int status, String msg) {
        this.status = (short) status;
        this.msg = msg;
    }

    public short getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }


    /**
     * 格式化
     *
     * @param values
     * @return
     */
    public String replacePlaceHolder(Object... values) {
        if (ObjectUtils.isEmpty(values)) {
            return msg;
        }
        return String.format(msg, values);
    }
}
