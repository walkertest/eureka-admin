package cn.springcloud.eureka.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.springcloud.eureka.service.EurekaClientManagerService;
import cn.springcloud.eureka.service.EurekaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.shared.Application;
import cn.springcloud.eureka.ResultMap;
import cn.springcloud.eureka.http.HttpUtil;

/**
 * 功能：
 * 1. 导航页，获取服务数量、节点数量等；
 * 2. 具体服务的展示与管理操作
 */
@RestController
@RequestMapping("eureka")
@Slf4j
public class EurekaAdminController {

    @Autowired
    EurekaService eurekaService;

    @Autowired
    EurekaClientManagerService eurekaClientManagerService;
	
	/**
	 * @description 获取服务数量和节点数量
	 */
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public ResultMap home(HttpServletRequest httpServletRequest){
        String cluster = eurekaService.getCluster(httpServletRequest);
        log.info("home req start cluster:{}",  cluster);
		List<Application> apps = eurekaService.getClusterInfo(cluster);
		int appCount = apps.size();
		int nodeCount = 0;
		int enableNodeCount = 0;
		for(Application app : apps){
			nodeCount += app.getInstancesAsIsFromEureka().size();
			List<InstanceInfo> instances = app.getInstances();
			for(InstanceInfo instance : instances){
				if(instance.getStatus().name().equals(InstanceStatus.UP.name())){
					enableNodeCount ++;
				}
			}
		}
		return ResultMap.buildSuccess().put("appCount", appCount).put("nodeCount", nodeCount).put("enableNodeCount", enableNodeCount);
	}
	
	/**
	 * @description 获取所有服务节点
	 */
	@RequestMapping(value = "apps", method = RequestMethod.GET)
	public ResultMap apps(HttpServletRequest httpServletRequest){
        String cluster = eurekaService.getCluster(httpServletRequest);
        log.info("apps req start cluster:{}", cluster);
		List<Application> apps = eurekaService.getClusterInfo(cluster);
		return ResultMap.buildSuccess().put("list", apps);
	}


    /**
	 * Take instance out of service	PUT /eureka/v2/apps/appID/instanceID/status?value=OUT_OF_SERVICE	HTTP Code:
	 * * 200 on success
	 * * 500 on failure
	 * DELETE /eureka/v2/apps/appID/instanceID/status?value=UP (The value=UP is optional, it is used as a suggestion for the fallback status due to removal of the override)
	 * @description 界面请求转到第三方服务进行状态变更
	 */
//	@RequestMapping(value = "status/{appName}", method = RequestMethod.POST)
//	public ResultMap status(@PathVariable String appName, String instanceId, String status){
//		Application application = eurekaClient.getApplication(appName);
//		InstanceInfo instanceInfo = application.getByInstanceId(instanceId);
//		instanceInfo.setStatus(InstanceStatus.toEnum(status));
//		Map<String, String> headers = new HashMap<>();
//		headers.put("Content-Type", "text/plain");
//		HttpUtil.post(instanceInfo.getHomePageUrl() + "service-registry/instance-status", status, headers);
//
//		return ResultMap.buildSuccess();
//	}


	//curl -v -X DELETE "127.0.0.1:9500/eureka/apps/CLIENT-A/192.168.1.101:client-a:7070/status?value=UP"
	//curl -v -X PUT "127.0.0.1:9500/eureka/apps/CLIENT-A/192.168.1.101:client-a:7070/status?value=OUT_OF_SERVICE"
	@RequestMapping(value = "status/{appName}", method = RequestMethod.POST)
	public ResultMap servStatus(HttpServletRequest httpServletRequest,
                                @PathVariable String appName, String instanceId, String status){
		log.info("修改节点状态，appName:{} instanceId:{} status:{}",
				new Object[] {appName, instanceId, status});

        String cluster = eurekaService.getCluster(httpServletRequest);
        String url = eurekaClientManagerService.getEurekaClusterServiceUrlByCluster(cluster);
        log.info("cluster:{} url:{}", cluster,url);
		//拼凑url
		String outOfServiceUrl = url + "apps/%s/%s/status?value=OUT_OF_SERVICE";
		String upUrl = url + "apps/%s/%s/status?value=UP";

		if(status.equalsIgnoreCase("UP")) {
			//curl -v -X DELETE "127.0.0.1:9500/eureka/apps/CLIENT-A/192.168.1.101:client-a:7070/status?value=UP"
			upUrl = String.format(upUrl, appName, instanceId);
			HttpUtil.delete(upUrl, null, null, null, "UTF-8");
		} else if(status.equalsIgnoreCase("OUT_OF_SERVICE")) {
			outOfServiceUrl = String.format(outOfServiceUrl,appName,instanceId);
			//curl -v -X PUT "127.0.0.1:9500/eureka/apps/CLIENT-A/192.168.1.101:client-a:7070/status?value=OUT_OF_SERVICE"
			HttpUtil.put(outOfServiceUrl,null,null,null,"UTF-8");
		}



		return ResultMap.buildSuccess();
	}
}
