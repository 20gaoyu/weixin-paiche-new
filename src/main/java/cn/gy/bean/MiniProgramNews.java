package cn.gy.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author : gaoyu
 * @CreateTime: 2021/12/2 11:12
 */
@Data
public class MiniProgramNews {
    @JSONField(name="touser")
    private String toUser;
    @JSONField(name="toparty")
    private String toParty;
    @JSONField(name="totag")
    private String toTag;
    @JSONField(name="msgtype")
    private String msgType;
    @JSONField(name="agentid")
    private String agentId;
    @JSONField(name="news")
    private News news;

}
