package com.tonysfriend.ms.bean;

import com.tonysfriend.ms.constant.Constants;
import com.tonysfriend.ms.util.DOMUtils;
import com.tonysfriend.ms.util.IpAddressUtil;
import com.tonysfriend.ms.util.StringUtil;
import org.dom4j.Document;

/**
 * <instance>
 * <instanceId>tonysfriends.lan:ribbon-consumer-1-2:5555</instanceId>
 * <hostName>tonysfriends.lan</hostName>
 * <app>RIBBON-CONSUMER-1-2</app>
 * <ipAddr>192.168.199.167</ipAddr>
 * <status>UP</status>
 * <overriddenstatus>UNKNOWN</overriddenstatus>
 * <port enabled="true">5555</port>
 * <securePort enabled="false">443</securePort>
 * <countryId>1</countryId>
 * <dataCenterInfo class="com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo">
 * <name>MyOwn</name>
 * </dataCenterInfo>
 * <leaseInfo>
 * <renewalIntervalInSecs>30</renewalIntervalInSecs>
 * <durationInSecs>90</durationInSecs>
 * <registrationTimestamp>1523714721962</registrationTimestamp>
 * <lastRenewalTimestamp>1523714721962</lastRenewalTimestamp>
 * <evictionTimestamp>0</evictionTimestamp>
 * <serviceUpTimestamp>1523714721252</serviceUpTimestamp>
 * </leaseInfo>
 * <metadata class="java.util.Collections$EmptyMap"/>
 * <homePageUrl>http://tonysfriends.lan:5555/</homePageUrl>
 * <statusPageUrl>http://tonysfriends.lan:5555/info</statusPageUrl>
 * <healthCheckUrl>http://tonysfriends.lan:5555/health</healthCheckUrl>
 * <vipAddress>ribbon-consumer-1-2</vipAddress>
 * <secureVipAddress>ribbon-consumer-1-2</secureVipAddress>
 * <isCoordinatingDiscoveryServer>false</isCoordinatingDiscoveryServer>
 * <lastUpdatedTimestamp>1523714721962</lastUpdatedTimestamp>
 * <lastDirtyTimestamp>1523714720912</lastDirtyTimestamp>
 * <actionType>ADDED</actionType>
 * </instance>
 *
 * @Author: tony.lu
 * @Date: 2018-04-23 上午 10:58
 */
public class InstanceId {

    String host;//"tonysfriends.lan:ribbon-consumer-1-2:5555";
    int port;
    String name;

    public static void setInstanceId(Document doc, String instanceId) {
        doc.getRootElement().element("instanceId").setText(instanceId);
    }

    public static void setIpAddr(Document doc, String ipAddr) throws Exception {
        //get cur ipAddr

        String localHostIp = IpAddressUtil.getLocalIp();

        if (StringUtil.isEmpty(ipAddr)) {
            ipAddr = localHostIp;
        }

        doc.getRootElement().element("ipAddr").setText(ipAddr);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InstanceId{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) throws Exception {

        Document doc = DOMUtils.getXMLByString(Constants.REGISTER_XML_STRING);
        System.out.println(doc.getPath());
        System.out.println(doc.getRootElement().element("port").getText());
        InstanceId.setInstanceId(doc, "tonysfriends.lan:ribbon-consumer-1-8:5555");
        InstanceId.setIpAddr(doc, null);
        System.out.println(DOMUtils.documentToString(doc, "UTF-8"));

    }

}
