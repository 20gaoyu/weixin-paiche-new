package cn.gy.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "fastdfs")
public class FastDFSConfig {
    String webServer;
    List<String> trackerServers;
}
