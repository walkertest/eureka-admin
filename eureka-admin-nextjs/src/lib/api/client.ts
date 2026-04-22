import axios, { AxiosError } from 'axios';
import { getCookie } from '@/lib/utils/cookies';

const apiClient = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL || '/eureka',
  withCredentials: true,
  timeout: 10000,
});

// 请求拦截器: 自动添加集群信息
apiClient.interceptors.request.use(
  (config) => {
    const cluster = getCookie('eureka_cluster');
    if (cluster) {
      config.headers['X-Eureka-Cluster'] = cluster;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器: 统一错误处理
apiClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    // 网络错误
    if (!error.response) {
      if (error.code === 'ECONNABORTED') {
        error.message = '请求超时,请检查网络连接';
      } else if (error.code === 'ERR_NETWORK') {
        error.message = '网络连接失败,请检查网络设置';
      } else {
        error.message = '网络错误,请稍后重试';
      }
      return Promise.reject(error);
    }

    // HTTP 错误状态码处理
    const status = error.response.status;
    switch (status) {
      case 401:
        error.message = '未授权,请重新登录';
        // window.location.href = '/login';
        break;
      case 403:
        error.message = '没有权限执行此操作';
        break;
      case 404:
        error.message = '请求的资源不存在';
        break;
      case 500:
        error.message = '服务器内部错误';
        break;
      case 502:
      case 503:
      case 504:
        error.message = '服务暂时不可用,请稍后重试';
        break;
      default:
        error.message = (error.response.data as any)?.message || `请求失败 (${status})`;
    }

    return Promise.reject(error);
  }
);

export default apiClient;
