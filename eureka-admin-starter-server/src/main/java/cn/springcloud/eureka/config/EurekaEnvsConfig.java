package cn.springcloud.eureka.config;

import cn.springcloud.eureka.model.EurekaEnvConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "eureka")
public class EurekaEnvsConfig {
    List<EurekaEnvConfig> envs;

    public List<EurekaEnvConfig> getEnvs() {
        return envs;
    }

    public void setEnvs(List<EurekaEnvConfig> envs) {
        this.envs = envs;
    }
}
