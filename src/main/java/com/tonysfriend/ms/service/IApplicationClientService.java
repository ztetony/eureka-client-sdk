package com.tonysfriend.ms.service;

import com.tonysfriend.ms.bean.Result;

public interface IApplicationClientService {

    /**
     * Register new application instance
     * POST /eureka/v2/apps/appID
     * Input: JSON/XML payload HTTP Code: 204 on success
     * @param url
     * @param content
     *
     * @return
     */
    public Result registerAppInstance(String url,String POST_METHOD, String ContentType, String content, int timeout);

    /**
     * Send application instance heartbeat
     * PUT /eureka/v2/apps/appID/instanceID
     * 	HTTP Code:
     * 200 on success
     * 404 if instanceID doesn’t exist
     *
     * @return
     */
    public Result sendHeartbeat(String url,String PUT_METHOD, String ContentType, String content, int timeout);

    /**
     * De-register application instance
     * DELETE /eureka/v2/apps/appID/instanceID
     * HTTP Code: 200 on success
     *
     * @return
     */
    public Result deleteAppInstance(String url, String DELETE_METHOD, String ContentType, String content, int timeout);

}
