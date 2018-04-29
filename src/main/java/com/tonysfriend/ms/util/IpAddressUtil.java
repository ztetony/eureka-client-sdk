package com.tonysfriend.ms.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @Author: tony.lu
 * @Date: 2018-04-23 上午 11:34
 */
public class IpAddressUtil {

    public static String getLocalHostAddress() {
        String hostAddress = null;
        try {
            InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
            hostAddress = address.getHostAddress();//192.168.0.121
//            InetAddress address1 = InetAddress.getByName("www.wodexiangce.cn");//获取的是该网站的ip地址，比如我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地
//            String hostAddress1 = address1.getHostAddress();//124.237.121.122
//            InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");//根据主机名返回其可能的所有InetAddress对象
//            for (InetAddress addr : addresses) {
//                System.out.println(addr);//www.baidu.com/14.215.177.38
//                //www.baidu.com/14.215.177.37
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hostAddress;
    }

    public static final String getLocalIp() throws Exception {
        String ipString = "";
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address && !ip.getHostAddress().equals("127.0.0.1")) {
                    return ip.getHostAddress();
                }
            }
        }
        return ipString;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(getLocalHostAddress());

        System.out.println(getLocalIp());

    }

}
