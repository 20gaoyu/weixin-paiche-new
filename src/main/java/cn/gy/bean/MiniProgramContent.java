package cn.gy.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author : gaoyu
 * @CreateTime: 2021/11/24 10:37
 */
@Data
public class MiniProgramContent {
    @JSONField(name="appid")
    private String appid;
    @JSONField(name="page")
    private String page;
    @JSONField(name="title")
    private String title;
}
