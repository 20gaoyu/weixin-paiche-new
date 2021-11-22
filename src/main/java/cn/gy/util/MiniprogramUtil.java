package cn.gy.util;

import cn.gy.bean.MiniprogramResult;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@Slf4j
public class MiniprogramUtil {

    /**
     * 微信登录成功后获取用户的openId和unionId
     * <p/>
     * Date 2018年9月29日 下午5:03:15
     * <p/>
     *
     * @author 网行天下
     */
    public static MiniprogramResult getOpenId(String code) {
        log.info("获取小程序OpenId和SessionKey数据开始code:" + code);
        MiniprogramResult miniprogramResult = null;
        String sessionData = "";
        String appid = "wxc0e2820b95ed1b06";
        String secret = "987d1815676757fb156580c3b76062eb";
        String authorizationCodeUrl= "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        // 微信的接口
        String url = String.format(authorizationCodeUrl, appid,secret, code);
        log.info(url);
        RestTemplate restTemplate = new RestTemplate();
        // 进行网络请求,访问url接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        // 根据返回值进行后续操作
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            sessionData = responseEntity.getBody();
            // 此处为返回json数据转换成javabean，可以自己查阅其他材料写
            miniprogramResult = JSONArray.parseObject(sessionData, MiniprogramResult.class);
            log.info("请求数据结束result:" + miniprogramResult);
            if (miniprogramResult != null) {
                return miniprogramResult;
            }
        }
        log.info("获取小程序OpenId和SessionKey数据结束result:" + miniprogramResult);
        return miniprogramResult;
    }

}

