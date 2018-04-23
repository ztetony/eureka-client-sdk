package com.tonysfriend.ms;

import com.tonysfriend.ms.bean.Result;
import com.tonysfriend.ms.constant.Constants;
import com.tonysfriend.ms.constant.MethodType;
import com.tonysfriend.ms.impl.ApplicationClientServiceImpl;
import com.tonysfriend.ms.util.PropertiesUtil;

public class Start {

    public static void run() {
        final ApplicationClientServiceImpl client = new ApplicationClientServiceImpl();

        String eureka = PropertiesUtil.getProperty("eureka.client.serviceUrl.defaultZone");
        String REGISTER_INSTANCE_URL = eureka + "apps/RIBBON-CONSUMER-1-2";

        Result registResult = client.registerAppInstance(Constants.REGISTER_INSTANCE_URL, "POST", "application/xml", Constants.REGISTER_XML_STRING, Constants.DEFAULT_TIMEOUT);
        System.out.println(registResult);

        if (registResult.isSuccess()) {

            //定时 heartbeat
            new Thread(new Runnable() {

                public void run() {

                    for (; ; ) {
                        System.out.println("...send heartbeat ...");
                        Result hbResult = client.sendHeartbeat(Constants.HEARTBEAT_URL, MethodType.HB_PUT.getName(), "text/plain", "", Constants.DEFAULT_TIMEOUT);
                        System.out.println(hbResult);
                        try {
                            Thread.sleep(Constants.HEARTBEAT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }

            }).start();

        }

    }

    public static void main(String[] args) {
        run();
    }

}