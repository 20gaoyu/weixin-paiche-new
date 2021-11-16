package cn.gy.start;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableSwagger2Doc
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ServletComponentScan(basePackages = {"cn.gy"})
@SpringBootApplication(scanBasePackages = {"cn.gy"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
