package cn.springcloud.eureka.service;

import cn.springcloud.eureka.config.EurekaClustersConfig;
import cn.springcloud.eureka.config.EurekaEnvsConfig;
import cn.springcloud.eureka.model.EurekaClusterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class EurekaClientManagerService implements InitializingBean {

    @Value("${spring.profiles.active:test}")
    String profile;

    @Autowired
    EurekaClustersConfig eurekaClientConfig;

//    @Value("${eureka.client.refresh.interval:10}")
//    public Integer refreshInterval;
//
//    @Value("${eureka.healthcheck.enabled:true}")
//    public Boolean healthCheckEnable;

//    Map<String, EurekaClient> eurekaClientMap = new HashMap<>();


    Map<String, EurekaClusterConfig> eurekaClusterConfigMap = new ConcurrentHashMap<>();


    @Autowired
    EurekaEnvsConfig eurekaEnvsConfig;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("EurekaClientManagerService afterPropertiesSet start. eurekaCluster size:{}", eurekaClientConfig.getClusters().size());
//        eurekaClientConfig.getClusters().forEach(clusterConfig -> {
//            log.info("clusterConfig: {}", clusterConfig);
//            EurekaClient eurekaClient = buildEurekaClient(
//                    clusterConfig.getServiceUrl(),
//                    healthCheckEnable,
//                    refreshInterval);
//            eurekaClientMap.put(clusterConfig.getName(),eurekaClient);
//        });

        eurekaClientConfig.getClusters().forEach(clusterConfig -> {
            eurekaClusterConfigMap.put(clusterConfig.getName(), clusterConfig);
        });
    }

    public EurekaClustersConfig getEurekaClientConfig() {
        return eurekaClientConfig;
    }

    public EurekaEnvsConfig getEurekaEnvsConfig() {
        return eurekaEnvsConfig;
    }

    public String getCurrentEnvName() {
        AtomicReference<String> env = new AtomicReference<>("");
        eurekaEnvsConfig.getEnvs().forEach((item) -> {
            if(profile.contains(item.getName())) {
                log.info("getCurrentProfile: {}", item);
                env.set(item.getChineseName());
            }
        });
        return env.get();
    }

//    public EurekaClient getEurekaClientByCluster(String cluster) {
//        return eurekaClientMap.get(cluster);
//    }

//    public EurekaClient buildEurekaClient(String eurekaSvrUrl, boolean healthCheck, int refreshInterval) {
//        log.info("create eurekaClient2 bean. url:{} healthCheck:{} refreshInterval:{}",eurekaSvrUrl,healthCheck,refreshInterval);
//        ConfigurationManager.getConfigInstance().setProperty("eureka.serviceUrl.default", eurekaSvrUrl);    //现网的注册中心
//        ConfigurationManager.getConfigInstance().setProperty("eureka.healthcheck.enabled", healthCheck);
//        ConfigurationManager.getConfigInstance().setProperty("eureka.client.refresh.interval",refreshInterval);   //10更新一次ip的状态.
//        ConfigurationManager.getConfigInstance().setProperty("eureka.registration.enabled",false);   //不注册
//
//        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
//        EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
//        log.info("create eurekaclient bean suc.");
//        return eurekaClient;
//    }

    public EurekaClusterConfig getEurekaClusterConfigByCluster(String cluster) {
        return eurekaClusterConfigMap.get(cluster);
    }

    public String getEurekaClusterServiceUrlByCluster(String cluster) {
        EurekaClusterConfig clusterConfig = eurekaClusterConfigMap.get(cluster);
        if(clusterConfig != null) {
            return clusterConfig.getServiceUrl();
        }
        return null;
    }

    public String getEurekaClusterSelfAdminUrlByCluster(String cluster) {
        EurekaClusterConfig clusterConfig = eurekaClusterConfigMap.get(cluster);
        if(clusterConfig != null) {
            return clusterConfig.getSelfadminUrl();
        }
        return null;
    }

//    private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
//        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
//
//        return new ApplicationInfoManager(instanceConfig, instanceInfo);
//    }
//
//    private synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, com.netflix.discovery.EurekaClientConfig clientConfig) {
//        return new DiscoveryClient(applicationInfoManager, clientConfig);
//    }


}
