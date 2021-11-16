package cn.gy.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author : chenws
 * @date : 2021/03/10
 */
public class ChannelUpdateParam {
    @Valid
    @NotEmpty(message = "域名属性不能为空!")
    @Size(min = 1, max = 100, message = "每次更新条数限制在100以内")
    private List<ChannelAttribute> channelAttributes;

    public List<ChannelAttribute> getChannelAttributes() {
        return channelAttributes;
    }

    public void setChannelAttributes(List<ChannelAttribute> channelAttributes) {
        this.channelAttributes = channelAttributes;
    }

}
