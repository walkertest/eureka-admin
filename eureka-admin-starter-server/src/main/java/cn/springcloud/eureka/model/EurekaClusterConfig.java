package cn.springcloud.eureka.model;

public class EurekaClusterConfig {
    private String name;
    private String chineseName;
    private String serviceUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    @Override
    public String toString() {
        return "EurekaClusterConfig{" +
                "name='" + name + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                '}';
    }
}
