package cn.springcloud.eureka.model;

import com.netflix.appinfo.InstanceInfo;

import java.util.List;
import java.util.Set;

public class EurekaApplication {
    private String name;
    private List<InstanceInfo> instance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InstanceInfo> getInstance() {
        return instance;
    }

    public void setInstance(List<InstanceInfo> instance) {
        this.instance = instance;
    }
}
