//package cn.springcloud.eureka.config;
//
//import com.netflix.appinfo.ApplicationInfoManager;
//import com.netflix.appinfo.EurekaInstanceConfig;
//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.appinfo.MyDataCenterInstanceConfig;
//import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
//import com.netflix.config.ConfigurationManager;
//import com.netflix.discovery.DefaultEurekaClientConfig;
//import com.netflix.discovery.DiscoveryClient;
//import com.netflix.discovery.EurekaClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import javax.annotation.PostConstruct;
//
///**
// * @description: add your desc
// * @author: walker
// * @create: 2020-03-27 16:42
// **/
//@Slf4j
//@Configuration
//public class EurekaClientConfig {
//
//	@Value("${eureka.serviceUrl.zhixu:}")
//	public String eurekaSvrUrl;
//
//    @Value("${eureka.serviceUrl.passport:}")
//    public String eurekaSvrUrl2;
//
//	@Value("${eureka.client.refresh.interval:10}")
//	public Integer refreshInterval;
//
//	@Value("${eureka.healthcheck.enabled:true}")
//	public Boolean healthCheckEnable;
//
//	private static ApplicationInfoManager applicationInfoManager;
//	private  EurekaClient eurekaClient;
//
//    private EurekaClient eurekaClientPassport;
//
//	public void setEurekaClient(EurekaClient eurekaClient) {
//		this.eurekaClient = eurekaClient;
//	}
//
//	public EurekaClient getEurekaClient() {
//		return eurekaClient;
//	}
//
//    public EurekaClient getEurekaClientPassport() {
//        return eurekaClientPassport;
//    }
//
//    public void setEurekaClientPassport(EurekaClient eurekaClientPassport) {
//        this.eurekaClientPassport = eurekaClientPassport;
//    }
//
//    /**
//     *     自己单例构造一个eureka client；
//     *     不要放入到bean context中，不然会有类型转换错误.
//     */
//	@PostConstruct
//	public void postInit() {
//		EurekaClient eurekaClientRet  = buildEurekaClient();
//		this.eurekaClient = eurekaClientRet;
//
//
//        //构建passport的eureka client
//        EurekaClient eurekaClientRet2  = getEurekaClient2(eurekaSvrUrl2, healthCheckEnable,refreshInterval);
//        this.eurekaClientPassport = eurekaClientRet2;
//
//	}
//
//	private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
//		if (applicationInfoManager == null) {
//			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
//			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
//		}
//
//		return applicationInfoManager;
//	}
//
//	private  synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, com.netflix.discovery.EurekaClientConfig clientConfig) {
//        EurekaClient eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
//		return eurekaClient;
//	}
//
//	//构造eureka client.
////	@Bean(name = "myEurekaClient")
//	public EurekaClient buildEurekaClient() {
//		if(this.eurekaClient == null) {
//			synchronized (EurekaClientConfig.class) {
//				if(this.eurekaClient == null) {
//					return getEurekaClient(eurekaSvrUrl, healthCheckEnable,refreshInterval);
//				} else {
//					return this.eurekaClient;
//				}
//			}
//		} else {
//			return eurekaClient;
//		}
//	}
//
//	public EurekaClient getEurekaClient(String eurekaSvrUrl,boolean healthCheck, int refreshInterval) {
//	    log.info("create eurekaClient bean. url:{} healthCheck:{} refreshInterval:{}",eurekaSvrUrl,healthCheck,refreshInterval);
//		ConfigurationManager.getConfigInstance().setProperty("eureka.serviceUrl.default", eurekaSvrUrl);    //现网的注册中心
//		ConfigurationManager.getConfigInstance().setProperty("eureka.healthcheck.enabled", healthCheck);
//		ConfigurationManager.getConfigInstance().setProperty("eureka.client.refresh.interval",refreshInterval);   //10更新一次ip的状态.
//		ConfigurationManager.getConfigInstance().setProperty("eureka.registration.enabled",false);   //不注册
//
//		ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
//        EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
//        log.info("create eurekaclient bean suc.");
//        return eurekaClient;
//	}
//
//    public EurekaClient getEurekaClient2(String eurekaSvrUrl,boolean healthCheck, int refreshInterval) {
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
//
//}
