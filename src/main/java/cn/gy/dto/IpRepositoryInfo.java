package cn.gy.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

/**
 * @desc:
 * @author: qingx
 * @create: 2021-03-11 17:14
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class IpRepositoryInfo {
    private String version;
    private String md5;
    private LocalDateTime gmtCreate;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
