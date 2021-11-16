package cn.gy.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author : chenws
 * @date : 2021/03/10
 */
public class ChannelAttribute {
    @NotBlank(message = "域名不能为空!")
    private String channel;
    private Integer vt;
    @JsonProperty(value = "path_index")
    private Integer pathIndex;
    private String pin;
    @JsonProperty(value = "domain_type")
    private String domainType;


    public Integer getVt() {
        return vt;
    }

    public void setVt(Integer vt) {
        this.vt = vt;
    }
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(Integer pathIndex) {
        this.pathIndex = pathIndex;
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

    @Override
    public String toString() {
        return "ChannelAttribute{" +
                "channel='" + channel + '\'' +
                ", vt=" + vt +
                ", pathIndex=" + pathIndex +
                ", pin='" + pin + '\'' +
                ", domainType='" + domainType + '\'' +
                '}';
    }
}
