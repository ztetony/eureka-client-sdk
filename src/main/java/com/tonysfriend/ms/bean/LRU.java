package com.tonysfriend.ms.bean;

import com.tonysfriend.ms.impl.ApplicationClientServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @Author: tony.lu
 * @Date: 2018-04-28 下午 06:38
 */
public class LRU {

    String host;
    int port;
    String name;
    Long cnt=0L;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public LRU(String host, Integer port, String name) {
        this.host = host;
        this.port = port;
        this.name = name;
    }

    public LRU(String host, Integer port, String name, Long cnt) {
        this.host = host;
        this.port = port;
        this.name = name;
        this.cnt = cnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LRU)) return false;

        LRU lru = (LRU) o;

        if (getPort() != lru.getPort()) return false;
        if (!getHost().equals(lru.getHost())) return false;
        return getName().equals(lru.getName());
    }

    @Override
    public int hashCode() {
        int result = getHost().hashCode();
        result = 31 * result + getPort();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LRU{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", cnt=" + cnt +
                '}';
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 12345;
        String name = "app1";

        LRU L1 = new LRU(host, port, name);
        LRU L2 = new LRU(host, port, name);

        System.out.println(L1.equals(L2));

        //选举一个使用最少的

        ArrayList<LRU> newLruList = new ArrayList<LRU>();
        newLruList.add(L1);
        newLruList.add(L2);

        Collections.sort(newLruList, new ApplicationClientServiceImpl.SortByCnt());

        for (LRU lru : newLruList) {
            System.out.println(lru.getCnt());
        }

    }

}
