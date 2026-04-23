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

export function EnvSelector() {
  const { currentEnv, setEnv } = useClusterStore();

  const { data, isLoading } = useQuery({
    queryKey: ['cluster-env-info'],
    queryFn: eurekaApi.getClusterEnvInfo,
  });

  const handleSelect = (env: { name: string; chineseName: string; url: string }) => {
    setEnv(env);
  };

  if (isLoading) {
    return (
      <div className="flex items-center gap-2">
        <span>环境:</span>
        <span className="text-green-400">加载中...</span>
      </div>
    );
  }

  // 找到当前环境的中文名称
  let currentEnvData = data?data.currentEnv:currentEnv;
  const currentEnvInfo = data?.envs.find(e => e.chineseName === currentEnvData);
  console.log("currentEnvData:{} currentEnvInfo:{}", currentEnvData,currentEnvInfo);

  return (
    <DropdownMenu>
      <DropdownMenuTrigger className="flex items-center gap-2 bg-transparent border-none outline-none cursor-pointer hover:opacity-80">
        <span>环境:</span>
        <span className="text-green-400">{currentEnvInfo?.chineseName || currentEnv || '未选择'}</span>
        <ChevronDown className="w-4 h-4" />
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        {data?.envs.map((env) => (
          <DropdownMenuItem
            key={env.name}
            onClick={() => handleSelect(env)}
            className="cursor-pointer"
          >
            {env.chineseName}
          </DropdownMenuItem>
        ))}
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
