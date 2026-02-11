package cn.springcloud.eureka.model;

import lombok.Data;

/**
 * {
 *                         "actionType": "ADDED",
 *                         "app": "ROTATE-VERIFY",
 *                         "countryId": 1,
 *                         "dataCenterInfo": {
 *                             "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
 *                             "name": "MyOwn"
 *                         },
 *                         "healthCheckUrl": "http://10.133.71.52:8866/health",
 *                         "homePageUrl": "http://10.133.71.52:8866/",
 *                         "hostName": "10.133.71.52",
 *                         "instanceId": "rotate-verify-gzhy1-high-vtpqq:rotate-verify:8866",
 *                         "ipAddr": "10.133.71.52",
 *                         "isCoordinatingDiscoveryServer": "false",
 *                         "lastDirtyTimestamp": "1764579636887",
 *                         "lastUpdatedTimestamp": "1769586032599",
 *                         "leaseInfo": {
 *                             "durationInSecs": 90,
 *                             "evictionTimestamp": 0,
 *                             "lastRenewalTimestamp": 1770780186142,
 *                             "registrationTimestamp": 1769586032598,
 *                             "renewalIntervalInSecs": 30,
 *                             "serviceUpTimestamp": 1769586032599
 *                         },
 *                         "metadata": {
 *                             "@class": "java.util.Collections$EmptyMap"
 *                         },
 *                         "overriddenstatus": "UNKNOWN",
 *                         "port": {
 *                             "$": 8866,
 *                             "@enabled": "true"
 *                         },
 *                         "securePort": {
 *                             "$": 443,
 *                             "@enabled": "false"
 *                         },
 *                         "secureVipAddress": "rotate-verify",
 *                         "status": "UP",
 *                         "statusPageUrl": "http://10.133.71.52:8866/info",
 *                         "vipAddress": "rotate-verify"
 *                     }
 */
@Data
public class EurekaInstance {
    private String app;
    private String hostName;
    private String ipAddr;
    private String status;
    //帮我生成一下剩余的字段
    private String instanceId;
    private String healthCheckUrl;
    private String homePageUrl;
    private String statusPageUrl;
    private String secureVipAddress;
    private String vipAddress;
    private LeaseInfo leaseInfo;
    private Long lastUpdatedTimestamp;
    private Metadata metadata;

    @Override
    public String toString() {
        return "EurekaInstance{" +
                "app='" + app + '\'' +
                ", hostName='" + hostName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", status='" + status + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", healthCheckUrl='" + healthCheckUrl + '\'' +
                ", homePageUrl='" + homePageUrl + '\'' +
                ", statusPageUrl='" + statusPageUrl + '\'' +
                ", secureVipAddress='" + secureVipAddress + '\'' +
                ", vipAddress='" + vipAddress + '\'' +
                ", leaseInfo=" + leaseInfo +
                '}';
    }
}

