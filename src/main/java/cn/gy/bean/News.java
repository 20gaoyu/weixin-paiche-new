package cn.gy.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author : gaoyu
 * @CreateTime: 2021/12/2 11:14
 */
@Data
public class News {
    @JSONField(name="articles")
    private List<Articles> list;
    @Data
    public static class Articles{
        @JSONField(name="title")
        private String title;
        @JSONField(name="appid")
        private String appid;
        @JSONField(name="url")
        private String url;
        @JSONField(name="pagepath")
        private String pagepath;
        @JSONField(name="description")
        private String description;
        @JSONField(name="picurl")
        private String picurl;
    }
}
