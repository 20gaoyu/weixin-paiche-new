package cn.gy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 
 * @author JDChen
 * @date 2019年7月28日 下午7:41:07
 */
@Slf4j
public class InterceptParmUtil {
	
    /**
     * 	获取RequestBody参数
     * @param request 请求
     * @return String
     */
    public static String getBodyParms(HttpServletRequest request) {
    	StringBuilder stringBuilder = new StringBuilder();
        String bodyParams = "";
        try (InputStream inputStream = request.getInputStream();
        	 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            if(inputStream != null) {
            	char[] charBuffer = new char[128];
            	int bytesRead = -1;
            	while((bytesRead = bufferedReader.read(charBuffer)) > 0) {
            		stringBuilder.append(charBuffer, 0, bytesRead);
            	}
            }else {
            	stringBuilder.append("");
    		}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			return bodyParams;
		}
        bodyParams = stringBuilder.toString();
		return bodyParams;
    }

    /**
     * 	获取PathVariable或RequestParam参数
     * @param request
     * @return
     */
    public static Map<String,String> getPathParams(HttpServletRequest request) {
    	Map<String, String> pathVaris = (Map<String, String>)request.getAttribute(HandlerMapping.
    			URI_TEMPLATE_VARIABLES_ATTRIBUTE); 
    	
    	Map<String, String[]> parameterMap = request.getParameterMap();
    	for (Map.Entry<String, String[]> ele : parameterMap.entrySet()) {
    		if(ele.getValue() != null && ArrayUtils.isNotEmpty(ele.getValue())) {
    			pathVaris.put(ele.getKey(), ele.getValue()[0]);
    		}
		}
    	return pathVaris;
    }
}