package cn.springcloud.eureka.config;

import cn.springcloud.eureka.model.EurekaClusterConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "eureka")
public class EurekaClientConfig1 {
    List<EurekaClusterConfig> clusters;

    public List<EurekaClusterConfig> getClusters() {
        return clusters;
    }

    public void setClusters(List<EurekaClusterConfig> clusters) {
        this.clusters = clusters;
    }
}
