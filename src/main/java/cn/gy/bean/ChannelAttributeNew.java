package cn.gy.bean;

import java.io.Serializable;

/**
 * @ClassName : ChannelAttribute
 * @Description :
 * @Author : Caoguodong
 * @Date: 2021-05-18
 */

public class ChannelAttributeNew implements Serializable {
    private static final long serialVersionUID = -9217149852910796367L;

    private String channel;

    private String pin;

    private String domainType;

    private String vt;

    private String pathIndex;

    private String baiduService;

    public String getBaiduService() {
        return baiduService;
    }

    public void setBaiduService(String baiduService) {
        this.baiduService = baiduService;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    public String getVt() {
        return vt;
    }

    public void setVt(String vt) {
        this.vt = vt;
    }

    public String getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(String pathIndex) {
        this.pathIndex = pathIndex;
    }
}
