package cn.springcloud.eureka.service;

import cn.springcloud.eureka.constant.Constants;
import cn.springcloud.eureka.http.HttpUtil;
import cn.springcloud.eureka.model.EurekaApplication;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class EurekaService {

    @Autowired
    EurekaClientManagerService eurekaClientManagerService;

    public String getCluster(HttpServletRequest httpServletRequest) {
        AtomicReference<String> cluster = new AtomicReference<>("zhixu");
        if(httpServletRequest.getCookies() != null) {
            Arrays.stream(httpServletRequest.getCookies()).anyMatch((cookie) -> {
                String cookieName = cookie.getName();
                if (cookieName.equalsIgnoreCase(Constants.EUREKA_CLUSTER_KEY)) {
                    cluster.set(cookie.getValue());
                    log.info("getCluster from cookie: {}={}", cookieName, cluster);
                    return true;
                }
                return false;
            });
        }
        return cluster.get();
    }


    public List<Application> getClusterInfo(String cluster) {
        List<Application> apps = eurekaClientManagerService.getEurekaClientByCluster(cluster).getApplications().getRegisteredApplications();
        return sortAppsResult(apps);
    }

    private static List<Application> sortAppsResult(List<Application> apps) {
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
        return apps;
    }

    /**
     * 通过api，自己请求
     * @param cluster
     * @return
     */
    public List<Application> getClusterInfoV2(String cluster) {
        String url = eurekaClientManagerService.getEurekaClusterSelfAdminUrlByCluster(cluster);
        String urlpath = "/eureka/apps";
        String resultUlr = url + urlpath;
        String result = HttpUtil.get(resultUlr);
        log.info("result: {}", result);

        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject applications = (JSONObject) jsonObject.get("applications");
        String applicationStr = applications.getString("application");;
        List<Application> apps = JSON.parseObject(applicationStr, new com.alibaba.fastjson.TypeReference<List<Application>>(){});
        log.info("apps: {}", apps);
        return sortAppsResult(apps);
    }


    public List<EurekaApplication> getClusterInfoResult(String cluster) {
        return convertApplications(getClusterInfoV2(cluster));
    }

    public List<EurekaApplication> convertApplications(List<Application> apps) {
        List<EurekaApplication> result = new ArrayList<>();
        if(apps!=null && !apps.isEmpty()) {
            apps.forEach((application) -> {
                EurekaApplication eurekaApplication = new EurekaApplication();
                eurekaApplication.setName(application.getName());
                eurekaApplication.setInstance(application.getInstancesAsIsFromEureka());
                result.add(eurekaApplication);
            });
        }
        return result;
    }


}
