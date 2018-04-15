package com.tonysfriend.ms.bean;

import java.io.Serializable;

public class Result implements Serializable {

    private int code = 500;
    private String content = "null";
    private boolean isSuccess = false;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return isSuccess==true;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", content='" + content + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }

    public void checkIfSuccess(int successCode) {
        isSuccess = this.code == successCode;
    }

}