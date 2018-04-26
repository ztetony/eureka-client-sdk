package com.tonysfriend.ms.util;

import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.bean.http.Header;
import com.tonysfriend.ms.bean.http.Param;
import com.tonysfriend.ms.constant.MethodType;
import com.tonysfriend.ms.impl.ApplicationClientServiceImpl;

/**
 * @Author: tony.lu
 * @Date: 2018-04-25 上午 11:27
 */
public class RestTemplate<T> {

    public Result<T> postForEntity(String url, String contentType, Header header, Param param, int timeout) throws Exception {
        ApplicationClientServiceImpl impl = new ApplicationClientServiceImpl();
        Result result = HttpClient.invoke(url, MethodType.REG_POST.getName(), contentType, header, param, timeout);

        return result;
    }

    public Result<T> getForEntity(String url, String contentType, Header header, Param param, int timeout) throws Exception {
        ApplicationClientServiceImpl impl = new ApplicationClientServiceImpl();
        Result result = HttpClient.invoke(url, MethodType.APPS_GET.getName(), contentType, header, param, timeout);

        return result;
    }

    public Result<T> invokeForEntity(String url, String method, String contentType, Header header, Param param, int timeout) throws Exception {
        ApplicationClientServiceImpl impl = new ApplicationClientServiceImpl();
        Result result = HttpClient.invoke(url, method, contentType, header, param, timeout);

        return result;
    }

}