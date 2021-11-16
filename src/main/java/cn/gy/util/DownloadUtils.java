package cn.gy.util;

import cn.gy.core.web.Result;
import cn.gy.core.web.ResultCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class DownloadUtils {

    public static void sendFailure(HttpServletResponse response, HttpStatus httpStatus, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json");
        response.setStatus(httpStatus.value());
        Result<String> result = new Result<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage(message);
        result.setData(message);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            log.warn("send error",e);
        }
    }

    private static String encodeFilename(String filename) {
        try {
            return URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("encode error",e);
        }
        return filename;
    }

    public static void transmitString(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String data, String fileName) {
        if(StringUtils.isBlank(data)){
            sendFailure(response, HttpStatus.NO_CONTENT, "无可下载内容，不执行下载动作");
        }
        response.setContentType("application/octet-stream");
        response.setContentLength(data.getBytes().length);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            IOUtils.write(data,response.getOutputStream(),"utf-8");
        } catch (Exception e) {
            log.error("下载失败！读取数据[{}]失败",data,e);
            sendFailure(response, HttpStatus.INTERNAL_SERVER_ERROR, "下载失败！读取数据失败。");
        }
    }

    public static void transmitFile(HttpServletRequest request, HttpServletResponse response, String filePath) {
        Path file = Paths.get(filePath);

        long size = file.toFile().length();
        String filename = file.getFileName().toString();
        String contentType = request.getServletContext().getMimeType(filename);
        String encodedFilename = encodeFilename(filename);

        response.setContentType(contentType);
        response.setContentLength((int) size);
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedFilename);

        try {
            Files.copy(file, response.getOutputStream());
        } catch (IOException e) {
            log.warn("transmit error",e);
            sendFailure(response, HttpStatus.INTERNAL_SERVER_ERROR, "下载失败！读取文件失败。");
        }
    }
}
