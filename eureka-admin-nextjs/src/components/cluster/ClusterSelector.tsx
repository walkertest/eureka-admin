'use client';

import { useQuery } from '@tanstack/react-query';
import { useClusterStore } from '@/lib/store/clusterStore';
import { eurekaApi } from '@/lib/api/eureka';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { ChevronDown } from 'lucide-react';
import { getCookie } from '@/lib/utils/cookies';

export function ClusterSelector() {
  const { currentCluster, setCluster } = useClusterStore();

  const { data, isLoading } = useQuery({
    queryKey: ['cluster-env-info'],
    queryFn: eurekaApi.getClusterEnvInfo,
  });

  const handleSelect = (cluster: { name: string; chineseName: string; url: string }) => {
    setCluster(cluster);
    // 刷新当前页面数据 (React Query 会自动重新请求)
    window.location.reload();
  };

  if (isLoading) {
    return (
      <div className="flex items-center gap-2">
        <span>集群:</span>
        <span className="text-green-400">加载中...</span>
      </div>
    );
  }

  const getCurrentCluster = (data: any) => {
    if (!data) return null;
    const cookieEurekaCluster = getCookie('eureka_cluster');
    for (const cluster of data.clusters) {
      if (cluster.name === cookieEurekaCluster) {
        return cluster;
      }
    }
    return data.clusters[0];  
  };

  let currentClusterData = getCurrentCluster(data);

  return (
    <DropdownMenu>
      <DropdownMenuTrigger className="flex items-center gap-2 bg-transparent border-none outline-none cursor-pointer hover:opacity-80">
        <span>集群:</span>
        <span className="text-green-400">{currentClusterData.chineseName}</span>
        <ChevronDown className="w-4 h-4" />
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        {data?.clusters.map((cluster) => (
          <DropdownMenuItem
            key={cluster.name}
            onClick={() => handleSelect(cluster)}
            className="cursor-pointer"
          >
            {cluster.chineseName}
          </DropdownMenuItem>
        ))}
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
