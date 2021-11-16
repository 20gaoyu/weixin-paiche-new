package cn.gy.controller;


import cn.gy.bean.*;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMAccountService;
import cn.gy.service.TMCustomerService;
import cn.gy.service.TMMemberService;
import cn.gy.service.TMMenuService;
import cn.gy.util.DigestUtil;
import cn.gy.util.EnvUtil;
import cn.gy.util.MiniprogramUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaoyu on 2019/07/18.
 */

@Api(tags = "账号管理")
@RestController
@RequestMapping("/aiverify/v1")
@Slf4j
public class TMMemberController {
    @Resource
    private TMMemberService tmMemberService;


    @GetMapping("/member/query")
    public Result<PageInfoVo<Member>> list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(required = false) String memberName) {
        log.info(" query start {}",memberName);
        PageHelper.startPage(page, size);
        List<Member> list = tmMemberService.getList(memberName);
        PageInfo<Member> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(pageInfo.getList(), pageInfo.getTotal()));
    }

    /**
     * 从mysql的日志字段表中插入数据
     * @return
     */
    @ApiOperation(value = "新增日志字段", notes = "插入日志字段数据")
    @PutMapping("/member/insert")
    public Result<String> add(@RequestBody @Valid Member request) {
        log.info(" insert start {}",request);
        try {
            return tmMemberService.add(request);
        } catch (Exception e) {
            log.error("insert Exception.", e);
        }
        return null;
    }

    /**
     * 从mysql的日志字段表中更新数据
     * @return
     */
    @ApiOperation(value = "更新日志字段", notes = "更新日志字段数据")
    @PutMapping("/member/update")
    public Result<String> update(@RequestBody @Valid Member request) {
        log.info("update start.{}",request);
        try {
            return tmMemberService.updateMember(request);
        } catch (Exception e) {
            log.error("update Exception.", e);
        }
        return null;
    }

    /**
     * 从mysql的日志字段表中删除数据
     * @return
     */
    @ApiOperation(value = "删除日志字段", notes = "删除日志字段数据")
    @GetMapping("/member/delete")
    public Result<String> delete(@RequestParam(required = false) Long id) {
        log.info("delete start. {}",id);
        try {
            return tmMemberService.deleteAllbyMember(id);
        } catch (Exception e) {
            log.error("delete Exception.", e);
        }
        return null;
    }
    @GetMapping("/member/findbyid")
    public Result<Member> find(@RequestParam(required = false) long id) {
        log.info("find start.{}",id);
        try {
            return ResultGenerator.genSuccessResult(tmMemberService.findById(id));
        } catch (Exception e) {
            log.error("findbyid Exception.", e);
        }
        return null;
    }
}
