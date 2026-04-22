// Eureka 实例信息
export interface EurekaInstance {
  instanceId: string;
  hostName: string;
  app: string;
  ipAddr: string;
  status: 'UP' | 'DOWN' | 'STARTING' | 'OUT_OF_SERVICE' | 'UNKNOWN';
  overriddenStatus: string;
  port: {
    $: number;
    '@enabled': string;
  };
  securePort: {
    $: number;
    '@enabled': string;
  };
  countryId: number;
  dataCenterInfo: {
    '@class': string;
    name: string;
  };
  leaseInfo: {
    renewalIntervalInSecs: number;
    durationInSecs: number;
    registrationTimestamp: number;
    lastRenewalTimestamp: number;
    evictionTimestamp: number;
    serviceUpTimestamp: number;
  };
  metadata: {
    zone?: string;
    [key: string]: any;
  };
  homePageUrl: string;
  statusPageUrl: string;
  healthCheckUrl: string;
  vipAddress: string;
  secureVipAddress: string;
  isCoordinatingDiscoveryServer: string;
  lastUpdatedTimestamp: number;
  lastDirtyTimestamp: number;
  actionType: string;
}

// Eureka 应用信息
export interface EurekaApp {
  name: string;
  instance: EurekaInstance[];
}

// Eureka Apps 响应
export interface EurekaAppResponse {
  list: EurekaApp[];
}

// Dashboard 指标
export interface DashboardMetrics {
  appCount: number;
  nodeCount: number;
  enableNodeCount: number;
  eurekaSelfLink: string;
}

// 集群信息
export interface ClusterInfo {
  name: string;
  chineseName: string;
  url: string;
}

// 环境信息
export interface EnvInfo {
  name: string;
  chineseName: string;
  url: string;
}

// 集群和环境信息响应
export interface ClusterEnvInfo {
  clusters: ClusterInfo[];
  envs: EnvInfo[];
  currentEnv: string;
}
