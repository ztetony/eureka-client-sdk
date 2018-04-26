package com.tonysfriend.ms.bean;

import com.tonysfriend.ms.constant.Constants;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private int code = Constants.CODE_SUCCESS;
    private T data;

    public Result() {

    }

    public Result(int code) {
        this.code = code;
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public boolean hasSuccess() {
        return this.code == Constants.CODE_SUCCESS;
    }

    public boolean checkBizSuccess(int bizCode) {
        return this.code == bizCode;

    }

}