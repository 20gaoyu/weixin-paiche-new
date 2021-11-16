package cn.gy.controller;


import cn.gy.bean.Car;
import cn.gy.bean.DispatchCarDetail;
import cn.gy.bean.Member;
import cn.gy.bean.PageInfoVo;
import cn.gy.core.web.Result;
import cn.gy.core.web.ResultGenerator;
import cn.gy.service.TMDispatchCarDetailService;
import cn.gy.service.TMMemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by gaoyu on 2019/07/18.
 */

@Api(tags = "账号管理")
@RestController
@RequestMapping("/aiverify/v1")
@Slf4j
public class TMDispatchCarDetailController {
    @Resource
    private TMDispatchCarDetailService tmDispatchCarDetailService;


    @GetMapping("/detail/query")
    public Result<PageInfoVo<DispatchCarDetail>> list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                                      @RequestParam(required = false) String name) {
        PageHelper.startPage(page, size);
        List<DispatchCarDetail> list = tmDispatchCarDetailService.getList(name);
        PageInfo<DispatchCarDetail> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(
                new PageInfoVo<>(pageInfo.getList(), pageInfo.getTotal()));
    }

    /**
     * 从mysql的日志字段表中插入数据
     * @return
     */
    @ApiOperation(value = "新增日志字段", notes = "插入日志字段数据")
    @PutMapping("/detail/insert")
    public Result<String> add(@RequestBody @Valid DispatchCarDetail request) {
        log.info("start.{}",request);
        try {
            return tmDispatchCarDetailService.add(request);
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
    @PutMapping("/detail/update")
    public Result<String> update(@RequestBody @Valid DispatchCarDetail request) {
        log.info("updata start.{}",request);
        try {
            return tmDispatchCarDetailService.updateDetail(request);
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
    @GetMapping("/detail/delete")
    public Result<String> delete(@RequestParam(required = false) Long id) {
        log.info("delete start. {}",id);
        try {
            return tmDispatchCarDetailService.deleteAllbyDetail(id);
        } catch (Exception e) {
            log.error("delete Exception.", e);
        }
        return null;
    }
    @GetMapping("/detail/findbyid")
    public Result<DispatchCarDetail> find(@RequestParam(required = false) long id) {
        log.info("find start.{}",id);
        try {
            return ResultGenerator.genSuccessResult(tmDispatchCarDetailService.findById(id));
        } catch (Exception e) {
            log.error("findbyid Exception.", e);
        }
        return null;
    }
}
