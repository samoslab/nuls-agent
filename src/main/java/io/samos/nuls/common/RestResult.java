package io.samos.nuls.common;

public class RestResult {
    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    Object result;



    public static RestResult ok(Object object) {
        return resultOf(ErrorCode.ERROR_CODE_SUCCESS, ErrorCode.ERROR_CODE_SUCCESS.getMsg(), object);
    }

    public static RestResult ok() {
        return resultOf(ErrorCode.ERROR_CODE_SUCCESS, ErrorCode.ERROR_CODE_SUCCESS.getMsg(), null);
    }

    public static RestResult resultOf(ErrorCode errorCode) {
        return resultOf(errorCode, errorCode.msg, null);
    }

    public static RestResult resultOf(ErrorCode errorCode, String msg) {
        return resultOf(errorCode, msg, null);
    }

    public static RestResult resultOf(ErrorCode errorCode, String msg, Object object) {
        RestResult result = new RestResult();
        result.code = errorCode.code;
        result.msg = msg;
        result.result = object;
        return result;
    }

    public static RestResult resultOf(int errorCode, String msg, Object object) {
        RestResult result = new RestResult();
        result.code = errorCode;
        result.msg = msg;
        result.result = object;
        return result;
    }
}
