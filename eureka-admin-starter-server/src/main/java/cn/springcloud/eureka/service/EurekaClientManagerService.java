package cn.springcloud.eureka.service;

import cn.springcloud.eureka.config.EurekaClientConfig1;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EurekaClientManagerService implements InitializingBean {
    @Autowired
    EurekaClientConfig1 eurekaClientConfig1;

    @Value("${eureka.client.refresh.interval:10}")
    public Integer refreshInterval;

    @Value("${eureka.healthcheck.enabled:true}")
    public Boolean healthCheckEnable;

    Map<String, EurekaClient> eurekaClientMap = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("EurekaClientManagerService afterPropertiesSet start. eurekaCluster size:{}", eurekaClientConfig1.getClusters().size());
        eurekaClientConfig1.getClusters().forEach(clusterConfig -> {
            log.info("clusterConfig: {}", clusterConfig);
            EurekaClient eurekaClient = buildEurekaClient(
                    clusterConfig.getServiceUrl(),
                    healthCheckEnable,
                    refreshInterval);
            eurekaClientMap.put(clusterConfig.getName(),eurekaClient);
        });
    }

    public EurekaClient getEurekaClientByCluster(String cluster) {
        return eurekaClientMap.get(cluster);
    }

    public EurekaClient buildEurekaClient(String eurekaSvrUrl, boolean healthCheck, int refreshInterval) {
        log.info("create eurekaClient2 bean. url:{} healthCheck:{} refreshInterval:{}",eurekaSvrUrl,healthCheck,refreshInterval);
        ConfigurationManager.getConfigInstance().setProperty("eureka.serviceUrl.default", eurekaSvrUrl);    //现网的注册中心
        ConfigurationManager.getConfigInstance().setProperty("eureka.healthcheck.enabled", healthCheck);
        ConfigurationManager.getConfigInstance().setProperty("eureka.client.refresh.interval",refreshInterval);   //10更新一次ip的状态.
        ConfigurationManager.getConfigInstance().setProperty("eureka.registration.enabled",false);   //不注册

        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
        log.info("create eurekaclient bean suc.");
        return eurekaClient;
    }

    private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();

        return new ApplicationInfoManager(instanceConfig, instanceInfo);
    }

    private synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, com.netflix.discovery.EurekaClientConfig clientConfig) {
        return new DiscoveryClient(applicationInfoManager, clientConfig);
    }
}
