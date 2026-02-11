package cn.springcloud.eureka.model;

public class EurekaEnvConfig {
    private String name;
    private String chineseName;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EurekaEnvConfig{" +
                "name='" + name + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
