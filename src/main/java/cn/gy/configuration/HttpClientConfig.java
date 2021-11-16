package cn.gy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : qinguoxing
 * @date : 2019/11/12
 */
@Configuration
public class HttpClientConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
