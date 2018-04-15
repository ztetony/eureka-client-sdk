package com.tonysfriend.ms.impl;

import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.constant.SuccessType;
import com.tonysfriend.ms.service.IApplicationClientService;
import com.tonysfriend.ms.util.HttpClient;

public class ApplicationClientServiceImpl implements IApplicationClientService {

    public Result registerAppInstance(String url, String method, String contentType, String content, int timeout) {

        Result result = null;
        try {
            result = HttpClient.invoke(url, method, contentType, content, timeout);
            result.checkIfSuccess(SuccessType.REGISTER_SUCCESS_204.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result();
        }

        return result;
    }

    public Result sendHeartbeat(String url, String method, String contentType, String content, int timeout) {
        Result result = null;
        try {
            result = HttpClient.invoke(url, method, contentType, content, timeout);
            result.checkIfSuccess(SuccessType.HEARTBEAT_SUCCESS_200.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result();
        }

        return result;
    }

    public Result deleteAppInstance(String url, String method, String contentType, String content, int timeout) {
        Result result = null;
        try {
            result = HttpClient.invoke(url, method, contentType, content, timeout);
            result.checkIfSuccess(SuccessType.CANCEL_SUCCESS_200.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result();
        }

        return result;
    }
}
