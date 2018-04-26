package com.tonysfriend.ms.constant;

public enum EurekaType {

    REGISTER_SUCCESS_204(204, "reg success", true),
    REGISTER_FAIL(204001, "reg fail", false),

    HEARTBEAT_SUCCESS_200(200, "send hb msg success", true),
    HEARTBEAT_FAIL_404(404, "send bh msg fail", false),

    CANCEL_SUCCESS_200(200, "cancel success", true),
    CANCEL_FAIL(200001, "cancel fail", false);

    int code;
    String desc;
    boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    EurekaType(int code, String desc, boolean success) {
        this.code = code;
        this.desc = desc;
        this.success = success;
    }

    @Override
    public String toString() {
        return "EurekaType{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", success=" + success +
                '}';
    }

}
