package cn.gy.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Order(1)
@Configuration
@ConditionalOnProperty(value = "executorConfig.enable", havingValue = "true")
public class ExecutorConfigurer {

    @Resource
    private ExecutorConfig executorConfig;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorConfig.getCorePoolSize());
        executor.setMaxPoolSize(executorConfig.getMaxPoolSize());
        executor.setKeepAliveSeconds(executorConfig.getKeepAliveTime());
        executor.setQueueCapacity(executorConfig.getQueueCapacity());
        executor.setThreadNamePrefix("TaskExecutor-");
        // 使用预定义的异常处理类
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
