package io.samos.nuls.common;

public enum ErrorCode {
    //General error
    ERROR_CODE_SUCCESS(0, "success"),
    ERROR_CODE_FAILED(6,"operation failed"),
    ERROR_CODE_SERVER_ERROR(8,"server error"),
    ERROR_CODE_LOGIN_FAILED(12, "login failed"),
    USER_ADD_FAILED(14, "add user failed")

    ;

    public int code;
    public String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if(errorCode.code == (code&0xff)) {
                return errorCode;
            }
        }
        return ERROR_CODE_SERVER_ERROR;
    }
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
