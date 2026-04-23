import apiClient from './client';
import type {
  DashboardMetrics,
  EurekaAppResponse,
  ClusterEnvInfo,
} from '@/types/eureka';

export const eurekaApi = {
  // Dashboard 数据
  getHomeMetrics: async () => {
    const response = await apiClient.get<DashboardMetrics>('/home');
    return response.data;
  },

  // 获取所有服务和实例
  getApps: async () => {
    const response = await apiClient.get<EurekaAppResponse>('/apps');
    return response.data;
  },

  // 更新实例状态
  updateStatus: async (
    appName: string,
    instanceId: string,
    status: 'UP' | 'OUT_OF_SERVICE'
  ) => {
    const body = new URLSearchParams({ instanceId, status });
    const response = await apiClient.post(
      `/status/${appName}`,
      body, 
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      }
      );
    return response.data;
  },

  // 获取集群和环境配置
  getClusterEnvInfo: async () => {
    const response = await apiClient.get<ClusterEnvInfo>('/getClusterAndEnvInfo');
    return response.data;
  },
};
