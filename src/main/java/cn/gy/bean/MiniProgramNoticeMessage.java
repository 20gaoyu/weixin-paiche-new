package cn.gy.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author : gaoyu
 * @CreateTime: 2021/11/24 10:37
 */
@Data
public class MiniProgramNoticeMessage {
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
    @JSONField(name="template_card")
    private TemplateCard templateCard;



}
