package com.tonysfriend.ms.impl;

import com.tonysfriend.ms.bean.InstanceId;
import com.tonysfriend.ms.bean.LRU;
import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.constant.Constants;
import com.tonysfriend.ms.constant.EurekaType;
import com.tonysfriend.ms.service.IApplicationClientService;
import com.tonysfriend.ms.util.DOMUtils;
import com.tonysfriend.ms.util.HttpClient;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationClientServiceImpl implements IApplicationClientService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ApplicationClientServiceImpl.class);

    private static ConcurrentHashMap<String, List<LRU>> cache = new ConcurrentHashMap<String, List<LRU>>(16);

    /**
     * @param url
     * @param method
     * @param contentType
     * @param content
     * @param timeout
     * @return
     */
    public Result registerAppInstance(String url, String method, String contentType, String content, int timeout) {

        Result result = null;

        try {
            result = HttpClient.invoke(url, method, contentType, content, timeout);
            result.setCode(EurekaType.REGISTER_SUCCESS_204.getCode());
            result.setData(EurekaType.REGISTER_SUCCESS_204.getDesc());
        } catch (Exception e) {
            LOGGER.error("registerAppInstance:{}", e);

            result.setCode(EurekaType.REGISTER_FAIL.getCode());
            result.setData(EurekaType.REGISTER_FAIL.getDesc());
        }

        return result;
    }

    /**
     * @param url
     * @param method
     * @param contentType
     * @param instanceId
     * @param timeout
     * @return
     * @throws Exception
     */
    public Result<String> registerAppInstance(String url, String method, String contentType, InstanceId instanceId, int timeout) throws Exception {
        Document document = DOMUtils.getXMLByString(Constants.REGISTER_XML_STRING);
        Result<String> result = new Result<String>();
        try {
            Element rootElement = document.getRootElement();
            rootElement.element("instanceId").setText(instanceId.getHost() + ":" + instanceId.getName() + ":" + instanceId.getPort());
            rootElement.element("hostName").setText(instanceId.getHost());
            rootElement.element("ipAddr").setText(instanceId.getHost());
            rootElement.element("port").setText(String.valueOf(instanceId.getPort()));
            rootElement.element("app").setText(instanceId.getName());
            rootElement.element("vipAddress").setText(instanceId.getName());
            rootElement.element("secureVipAddress").setText(instanceId.getName());
            rootElement.element("lastUpdatedTimestamp").setText(System.currentTimeMillis() + "");
            rootElement.element("healthCheckUrl").setText("http://" + instanceId.getHost() + ":" + instanceId.getPort() + "/health");
            String content = DOMUtils.documentToString(document, "UTF-8");

            result = HttpClient.invoke(url, method, contentType, content, timeout);
            result.setCode(EurekaType.REGISTER_SUCCESS_204.getCode());
            result.setData(EurekaType.REGISTER_SUCCESS_204.getDesc());

        } catch (Exception e) {
            LOGGER.error("registerAppInstance:{}", e);

            result.setCode(EurekaType.REGISTER_FAIL.getCode());
            result.setData(EurekaType.REGISTER_FAIL.getDesc() + " " + e.getMessage());
        }

        return result;
    }

    public Result<String> sendHeartbeat(String url, String method, String contentType, String content, int timeout) {
        Result<String> result = new Result<String>();
        try {
            result = HttpClient.invoke(url, method, contentType, content, timeout);
            result.setCode(EurekaType.HEARTBEAT_SUCCESS_200.getCode());
            result.setData(EurekaType.HEARTBEAT_SUCCESS_200.getDesc());
        } catch (Exception e) {
            LOGGER.error("sendHeartbeat:{}", e);

            result.setCode(EurekaType.HEARTBEAT_FAIL_404.getCode());
            result.setData(EurekaType.HEARTBEAT_FAIL_404.getDesc() + " " + e.getMessage());
        }

        return result;
    }

    public Result<String> deleteAppInstance(String url, String method, String contentType, String content, int timeout) {
        Result<String> result = new Result<String>();
        try {
            result = HttpClient.invoke(url, method, contentType, content, timeout);
            result.setCode(EurekaType.CANCEL_SUCCESS_200.getCode());
            result.setData(EurekaType.CANCEL_SUCCESS_200.getDesc());
        } catch (Exception e) {
            LOGGER.error("deleteAppInstance:{}", e);

            result.setCode(EurekaType.CANCEL_FAIL.getCode());
            result.setData(EurekaType.CANCEL_FAIL.getDesc() + " " + e.getMessage());
        }

        return result;
    }

    /**
     * @param instanceUrl http://10.45.32.93:1111/eureka/apps/ZIOT.DMS.1.0.0.DAILY
     * @param timeout
     * @return
     */
    public Result<InstanceId> getInstance(String instanceUrl, int timeout) {
        Result<InstanceId> result = new Result<InstanceId>();
        Result<String> xmlContentResult = new Result<String>();

        String getAppByIdUrl = "http://10.45.32.93:1111/eureka/apps/ZIOT.DMS.1.0.0.DAILY";

        try {
            xmlContentResult = HttpClient.doGet(instanceUrl, timeout);
            xmlContentResult.setCode(EurekaType.CANCEL_SUCCESS_200.getCode());

            String xmlString = xmlContentResult.getData();
            Document doc = DOMUtils.getXMLByString(xmlString);
            List<Element> elements = doc.getRootElement().elements("instance");

            for (Element element : elements) {
                String status = element.element("status").getText();
                if ("UP".equals(status)) {
                    String text = element.element("instanceId").getText();

                    String[] arrs = text.split(":");
                    InstanceId instanceId = new InstanceId();
                    instanceId.setHost(arrs[0]);
                    instanceId.setName(arrs[1]);
                    instanceId.setPort(Integer.parseInt(arrs[2]));
                    result.setData(instanceId);

                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("getInstance:{}", e);
            result.setCode(Constants.CODE_SERVER_ERROR);
        }

        return result;
    }

    /**
     * @param instanceUrl http://10.45.32.93:1111/eureka/apps/ZIOT.DMS.1.0.0.DAILY
     * @param timeout
     * @return
     */
    public Result<InstanceId> getInstance(String instanceUrl, String app, int timeout) {
        Result<InstanceId> result = new Result<InstanceId>();
        Result<String> xmlContentResult = new Result<String>();

        try {
            xmlContentResult = HttpClient.doGet(instanceUrl, timeout);
            xmlContentResult.setCode(EurekaType.CANCEL_SUCCESS_200.getCode());

            String xmlString = xmlContentResult.getData();
            Document doc = DOMUtils.getXMLByString(xmlString);
            List<Element> elements = doc.getRootElement().elements("instance");

            List<LRU> lrus = cache.get(app);
            if (lrus == null) {
                lrus = new ArrayList<LRU>();
            }

            ArrayList<LRU> newLruList = new ArrayList<LRU>();

            for (Element element : elements) {
                String instanceId = element.element("instanceId").getText();
                String status = element.element("status").getText();
                if ("UP".equals(status)) {
                    String[] arrs = instanceId.split(":");
                    LRU newLRU = new LRU(arrs[0], new Integer(arrs[2]), arrs[1]);
                    newLruList.add(newLRU);
                }

            }//for

            for (int i = 0; i < lrus.size(); i++) {
                LRU lru = lrus.get(i);
                for (int j = 0; j < newLruList.size(); j++) {
                    LRU newLRU = newLruList.get(j);
                    if (lru.equals(newLRU)) {
                        newLruList.set(j,lru);
                    }
                }
            }//for

            //选举一个使用最少的
            Collections.sort(newLruList, new SortByCnt());

            cache.put(app, newLruList);

            if (newLruList != null && newLruList.size() > 0) {
                LRU lru = newLruList.get(0);

                //times cnt add 1
                lru.setCnt(lru.getCnt() + 1);
                InstanceId instanceId = new InstanceId();
                instanceId.setHost(lru.getHost());
                instanceId.setName(lru.getName());
                instanceId.setPort(lru.getPort());

                result.setData(instanceId);
            }

        } catch (Exception e) {
            LOGGER.error("getInstance:{}", e);
            result.setCode(Constants.CODE_SERVER_ERROR);
        }

        return result;
    }

    public static class SortByCnt implements Comparator {
        public int compare(Object o1, Object o2) {
            LRU s1 = (LRU) o1;
            LRU s2 = (LRU) o2;
            return s1.getCnt().compareTo(s2.getCnt());
//          if (s1.getCnt() > s2.getCnt())
//           return 1;
//          return -1;
        }
    }

    public static void main(String[] args) throws Exception {
        ApplicationClientServiceImpl client = new ApplicationClientServiceImpl();
        String url = "http://ZIOT.DMS.1.0.0.DAILY/device/add";
        //parse vipAddress
//        int pos1 = url.indexOf("http://") + 7;
//        String url1 = url.substring(pos1);
//        int pos2 = url1.indexOf("/");
//
//        String vipAddress = url1.substring(0, pos2);
//        System.out.println(vipAddress);
//
//        String getAppByIdUrl = "http://10.45.32.93:1111/eureka/apps/ZIOT.DMS.1.0.0.DAILY";
//
//        String content = null;
//        Result result = HttpClient.doGet(getAppByIdUrl, "GET", Constants.DEFAULT_TIMEOUT);
//        System.out.println(result.getContent());

        Result result = client.getInstance("http://10.45.32.93:1111/eureka/apps/ZIOT.DMS.1.0.0.DAILY", Constants.DEFAULT_TIMEOUT);
        System.out.println(result);
    }

}
