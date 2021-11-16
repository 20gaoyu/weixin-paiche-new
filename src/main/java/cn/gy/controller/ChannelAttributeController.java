package cn.gy.controller;


import cn.gy.constant.SysConstant;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.dto.ChannelUpdateParam;
import cn.gy.exception.RequestParamException;
import cn.gy.service.ChannelAttributeService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * @author chenws
 * @version 1.0
 * @date 2021/3/8
 */
@RestController
@RequestMapping(value = "/api/v1")
public class ChannelAttributeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelAttributeController.class);
    @Autowired
    private ChannelAttributeService channelAttributeService;


    @ApiOperation(value = "channel特殊属性更新接口-/channelAttribute/update", notes = "发送http请求,将域名属性更新或插入到mysql表,表已在该channel则更新，否则执行插入")
    @PostMapping("/channelAttribute/update")
    public Result<String> channelAttributeUpdate(@RequestBody @Validated ChannelUpdateParam channelUpdateParam, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultGenerator.genFailResult(bindingResult.getFieldError().getDefaultMessage());
        }
        String result;
        try {
            result = channelAttributeService.updateAndInsert(channelUpdateParam);
        }catch (Exception e){
            LOGGER.error("Update failed.Exception:",e);
            return ResultGenerator.genFailResult("Update failed.Exception:"+e);
        }
        return ResultGenerator.genSuccessResult(result);
    }


    @ApiOperation(value = "channel特殊属性删除接口-/channelAttribute/delete", notes = "发送http请求,删除mysql表对应的channel")
    @DeleteMapping("/channelAttribute/delete")
    public Result<String> channelAttributeDelete(@RequestParam(value = "channel") String channel) {
        if (StringUtils.isBlank(channel) || !Pattern.matches(SysConstant.CHANNEL_REGEX,channel)) {
            return ResultGenerator.genFailResult("Channel can not be null or empty or invalid,please check!");
        }
        try {
            channelAttributeService.deleteChannel(channel);
        }catch (Exception e){
            LOGGER.error("Delete failed! Exception:",e);
            return ResultGenerator.genFailResult("Delete failed! Exception:"+e);
        }
        return ResultGenerator.genSuccessResult("Successfully deleted");
    }

    @ApiOperation(value = "channel特殊属性查询接口-/channelAttribute/query", notes = "发送http请求,查询channel属性值")
    @GetMapping("/channelAttribute/query")
    public ObjectNode channelAttributeQuery(@RequestParam(value = "channel") String channel) throws RequestParamException {
        if (StringUtils.isBlank(channel) || !Pattern.matches(SysConstant.CHANNEL_REGEX,channel)) {
            throw new RequestParamException("Channel cannot be null or empty or invalid,please check!");
        }
        return channelAttributeService.queryChannelAttribute(channel);
    }
}
