package cn.gy.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : gaoyu
 * @CreateTime: 2021/12/2 9:55
 */
@Data
public class TemplateCard {
    @JSONField(name="card_type")
    private String cardType;
    @JSONField(name="action_menu")
    private ActionMenu actionMenu;
    @JSONField(name="horizontal_content_list")
    private List<HorizontalContent> horizontalContentList;
    @JSONField(name="jump_list")
    private List<Jump> jump;
    @JSONField(name="card_action")
    private CardAction cardAction;
    @Data
    public static class ActionMenu{
        @JSONField(name="action_list")
        private List<Action> actionList;
        public ActionMenu(){
            actionList=new ArrayList<>();
            Action action=new Action();
            action.setText("接受推送");
            action.setKey("A");
            Action action1=new Action();
            action1.setText("不再推送");
            action1.setKey("B");
            actionList.add(action);
            actionList.add(action1);
        }
    }
    @Data
    public static class HorizontalContent{
        @JSONField(name="keyname")
        private String key;
        @JSONField(name="value")
        private String value;
    }
    @Data
    public static class Jump{
        @JSONField(name="type")
        private String type;
        @JSONField(name="title")
        private String title;
        @JSONField(name="url")
        private String url;
    }
    @Data
    public static class CardAction{
        @JSONField(name="type")
        private String type;
        @JSONField(name="appid")
        private String appid;
        @JSONField(name="url")
        private String url;
        @JSONField(name="pagepath")
        private String pagepath;
    }
    @Data
    public static class Action{
        @JSONField(name="text")
        private String text;
        @JSONField(name="key")
        private String key;
    }
}
