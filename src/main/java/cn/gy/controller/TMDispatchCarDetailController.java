package cn.gy.controller;


import cn.gy.bean.Car;
import cn.gy.bean.DispatchCarDetail;
import cn.gy.bean.DispatchCarDetailVo;
import cn.gy.bean.DispatchCarDetailWebVo;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private static final String rootFilePath = "/usr/local/webserver/nginx/html/";

    @GetMapping("/detail/query")
    public Result<PageInfoVo<DispatchCarDetailWebVo>> list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,
                                                           @RequestParam(required = false) String name) {
        PageHelper.startPage(page, size);
        List<DispatchCarDetailWebVo> list = tmDispatchCarDetailService.getList(name);
        PageInfo<DispatchCarDetailWebVo> pageInfo = new PageInfo<>(list);
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
    public Result<String> update(@RequestBody @Valid DispatchCarDetailVo request) {
        log.info("updata start.{}",request);
        try {
            return tmDispatchCarDetailService.updateDetailWeb(request);
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
    @GetMapping("/detail/export")
    public Result<String> export(@RequestParam(required = true) String startTime,@RequestParam(required = true) String endTime) {
        log.info("find export.{},{}",startTime,endTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatToday = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = formatToday.format(new Date());
        String fileName = date+"_detail.xls";
        String path=rootFilePath+fileName;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet1 = workbook.createSheet("sheet1");
            List<DispatchCarDetail> usersList = tmDispatchCarDetailService.getListByTime(startTime,endTime);
          //          .queryForList("SELECT u.id,u.userName,u.userAge,u.userAddress FROM user u");
            // 设置列宽
            sheet1.setColumnWidth(0, 30*256);
            // 合并单元格   参数说明：1：开始行 2：结束行  3：开始列 4：结束列
            sheet1.addMergedRegion(new CellRangeAddress(0,0,0,3));

            String[] headers = {"ID", "申请人", "使用者", "开始时间","结束时间","是否评价","用车原因",
                    "目的地","一级审核","二级审核","审核状态","操作","创建时间","部门","电话","用车人数"};
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm:ss"));
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            // 下两行共同构成了背景色
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderTop(BorderStyle.THIN);

            // 第一行
            HSSFRow row0 = sheet1.createRow(0);
            HSSFCell row0cell0 = row0.createCell(0);
            HSSFRichTextString text = new HSSFRichTextString("detail");
            row0cell0.setCellValue(text);
            row0cell0.setCellStyle(style);

            // 第二行
            HSSFRow row1 = sheet1.createRow(1);
            for(int i=0;i<headers.length;i++){
                HSSFCell cell = row1.createCell(i);
                HSSFRichTextString text2 = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text2);
                cell.setCellStyle(style);
            }
            // 第三行及以后
            int rowNum = 2;
            for(DispatchCarDetail dispatchCarDetail : usersList){
                int rowNumCellN = 0;
                HSSFRow rowRowNum = sheet1.createRow(rowNum);
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getId()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getApplicant()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getUser()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(format.format(dispatchCarDetail.getStartTime()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(format.format(dispatchCarDetail.getEndTime()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getIfComment()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getUseReason()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getDestination()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getOneAudit()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getTwoAudit()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getStatus()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getOperation()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(format.format(dispatchCarDetail.getCreateTime()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getDepartmentName()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getTelephone()));
                rowRowNum.createCell(rowNumCellN++).setCellValue(String.valueOf(dispatchCarDetail.getUseNumber()));
                rowNum++;
            }
//            isDirExist(rootFilePath+formatToday.format(new Date()));
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream); // 写入磁盘
            outputStream.close();
            workbook.close();
        } catch (Exception e) {
            log.info("导出失败",e);
        }
        return ResultGenerator.genSuccessResult("./"+fileName);
    }
    public void isDirExist(String fileDir) {
        File file = new File(fileDir);
        if(!file.exists()) {
            file.mkdir();
        }
    }
}
