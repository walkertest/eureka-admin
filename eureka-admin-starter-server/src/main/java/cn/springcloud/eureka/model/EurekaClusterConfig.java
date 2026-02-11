package cn.springcloud.eureka.model;

public class EurekaClusterConfig {
    private String name;
    private String chineseName;
    private String serviceUrl;
    private String selfadminUrl;

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

    public String getSelfadminUrl() {
        return selfadminUrl;
    }

    public void setSelfadminUrl(String selfadminUrl) {
        this.selfadminUrl = selfadminUrl;
    }

    @Override
    public String toString() {
        return "EurekaClusterConfig{" +
                "name='" + name + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                ", selfadminUrl='" + selfadminUrl + '\'' +
                '}';
    }
}
