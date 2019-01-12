package cn.springcloud.eureka.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import cn.springcloud.eureka.ResultMap;
import cn.springcloud.eureka.http.HttpUtil;

@RestController
@RequestMapping("eureka")
@Slf4j
public class EurekaClientController {

	@Value("${eureka.client.serviceUrl.defaultZone}")
	String defaultZone;

	@Resource
	private EurekaClient eurekaClient;
	
	/**
	 * @description 获取服务数量和节点数量
	 */
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public ResultMap home(){
		List<Application> apps = eurekaClient.getApplications().getRegisteredApplications();
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
	public ResultMap apps(){
		List<Application> apps = eurekaClient.getApplications().getRegisteredApplications();
		Collections.sort(apps, new Comparator<Application>() {
	        public int compare(Application l, Application r) {
	            return l.getName().compareTo(r.getName());
	        }
	    });
		for(Application app : apps){
			Collections.sort(app.getInstances(), new Comparator<InstanceInfo>() {
		        public int compare(InstanceInfo l, InstanceInfo r) {
		            return l.getPort() - r.getPort();
		        }
		    });
		}
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
	public ResultMap servStatus(@PathVariable String appName, String instanceId, String status){
		log.info("appName:{} instanceId:{} status:{} defaultZone:{}",
				new Object[] {appName, instanceId, status,defaultZone});

		//拼凑url
		String url= defaultZone;
		String outOfServiceUrl = defaultZone + "apps/%s/%s/status?value=OUT_OF_SERVICE";
		String upUrl = defaultZone + "apps/%s/%s/status?value=UP";

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
