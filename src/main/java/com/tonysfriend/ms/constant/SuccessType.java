package com.tonysfriend.ms.constant;

public enum SuccessType {

    REGISTER_SUCCESS_204(204, "ok", true),
    HEARTBEAT_SUCCESS_200(200, "ok", true),
    HEARTBEAT_FAIL_404(404, "instanceID doesn’t exist", false),
    CANCEL_SUCCESS_200(200, "ok", true);

    int code;
    String name;
    boolean isSuccess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    SuccessType(int code, String name, boolean isSuccess) {
        this.code = code;
        this.name = name;
        this.isSuccess = isSuccess;
    }

}
