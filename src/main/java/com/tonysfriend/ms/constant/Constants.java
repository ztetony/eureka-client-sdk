package com.tonysfriend.ms.constant;

public class Constants {

    public static final int HEARTBEAT_INTERVAL = 3000;
    public static final int DEFAULT_TIMEOUT = 10000;
    public static final String REGISTER_INSTANCE_URL="http://localhost:8888/eureka/apps/RIBBON-CONSUMER-1-2";
    public static final String HEARTBEAT_URL="http://localhost:8888/eureka/apps/RIBBON-CONSUMER-1-2/tonysfriends.lan:ribbon-consumer-1-2:5555";
    public static final String CANCEL_URL="http://localhost:8888/eureka/v2/apps/RIBBON-CONSUMER-1-2/tonysfriends.lan:ribbon-consumer-1-2:5555";

    public static final String REGISTER_XML_STRING = "<instance>\n" +
            "    <instanceId>tonysfriends.lan:ribbon-consumer-1-2:5555</instanceId>\n" +
            "    <hostName>tonysfriends.lan</hostName>\n" +
            "    <app>RIBBON-CONSUMER-1-2</app>\n" +
            "    <ipAddr>192.168.199.167</ipAddr>\n" +
            "    <status>UP</status>\n" +
            "    <overriddenstatus>UNKNOWN</overriddenstatus>\n" +
            "    <port enabled=\"true\">5555</port>\n" +
            "    <securePort enabled=\"false\">443</securePort>\n" +
            "    <countryId>1</countryId>\n" +
            "    <dataCenterInfo class=\"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo\">\n" +
            "        <name>MyOwn</name>\n" +
            "    </dataCenterInfo>\n" +
            "    <leaseInfo>\n" +
            "        <renewalIntervalInSecs>30</renewalIntervalInSecs>\n" +
            "        <durationInSecs>90</durationInSecs>\n" +
            "        <registrationTimestamp>1523714721962</registrationTimestamp>\n" +
            "        <lastRenewalTimestamp>1523714721962</lastRenewalTimestamp>\n" +
            "        <evictionTimestamp>0</evictionTimestamp>\n" +
            "        <serviceUpTimestamp>1523714721252</serviceUpTimestamp>\n" +
            "    </leaseInfo>\n" +
            "    <metadata class=\"java.util.Collections$EmptyMap\"/>\n" +
            "    <homePageUrl>http://tonysfriends.lan:5555/</homePageUrl>\n" +
            "    <statusPageUrl>http://tonysfriends.lan:5555/info</statusPageUrl>\n" +
            "    <healthCheckUrl>http://tonysfriends.lan:5555/health</healthCheckUrl>\n" +
            "    <vipAddress>ribbon-consumer-1-2</vipAddress>\n" +
            "    <secureVipAddress>ribbon-consumer-1-2</secureVipAddress>\n" +
            "    <isCoordinatingDiscoveryServer>false</isCoordinatingDiscoveryServer>\n" +
            "    <lastUpdatedTimestamp>1523714721962</lastUpdatedTimestamp>\n" +
            "    <lastDirtyTimestamp>1523714720912</lastDirtyTimestamp>\n" +
            "    <actionType>ADDED</actionType>\n" +
            "</instance>";

}
