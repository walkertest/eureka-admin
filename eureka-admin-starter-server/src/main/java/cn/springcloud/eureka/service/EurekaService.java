package cn.springcloud.eureka.service;

import cn.springcloud.eureka.constant.Constants;
import cn.springcloud.eureka.model.EurekaApplication;
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
