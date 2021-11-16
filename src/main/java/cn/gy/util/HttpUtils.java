package cn.gy.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;

@Slf4j
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static final String AUTH_PREFIX = "Basic";

    public static JSONObject sendHttpRequest(String user, String password, String json, HttpRequestBase request) {
        JSONObject response = new JSONObject();
        response.put("body", null);
        response.put("code", 0);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            request.setHeader("Authorization", getAuthorization(user, password));
            request.setHeader("X-Requested-By", user);
            if (json != null) {
                ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(json));
            }
            HttpResponse httpResponse = httpClient.execute(request);
            response.put("body", parseResponseBody(httpResponse));
            response.put("code", parseResponseStatus(httpResponse));
        } catch (IOException e) {
            log.warn("send http request error",e);
        }
        return response;
    }

    public static String sendHttpRequestAndGetBody(String user, String password, String json, HttpRequestBase request) {
        return sendHttpRequest(user, password, json, request).getString("body");
    }

    public static int sendHttpRequestAndGetCode(String user, String password, String json, HttpRequestBase request) {
        return sendHttpRequest(user, password, json, request).getIntValue("code");
    }

    public static int parseResponseStatus(HttpResponse response) {
        int statusCode = 0;
        if (response != null) {
            statusCode = response.getStatusLine().getStatusCode();
        }
        LOGGER.debug("---------------------------------------------");
        LOGGER.debug(statusCode + "");
        return statusCode;
    }

    public static String parseResponseBody(HttpResponse response) {
        String result = null;
        try {
            if (response != null) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            log.warn("parse ResponseBody error",e);
        }
        LOGGER.debug("---------------------------------------------");
        LOGGER.debug(result);
        return result;
    }


    public static String putHttpRequest(String user, String password, String url, String json) {
        HttpPut request = new HttpPut(url);
        return sendHttpRequestAndGetBody(user, password, json, request);
    }

    public static String getHttpRequest(String user, String password, String url) {
        HttpGet request = new HttpGet(url);
        return sendHttpRequestAndGetBody(user, password, null, request);
    }

    public static String postHttpRequest(String user, String password, String url, String json) {
        HttpPost request = new HttpPost(url);
        return sendHttpRequestAndGetBody(user, password, json, request);
    }

    public static int deleteHttpRequest(String user, String password, String url) {
        HttpDelete request = new HttpDelete(url);
        return sendHttpRequestAndGetCode(user, password, null, request);
    }

    private static String getAuthorization(String user, String password) {
        String auth = user + ":" + password;
        return AUTH_PREFIX + " " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
