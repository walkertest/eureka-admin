'use client';

import { useState, useMemo, useEffect } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { ConditionalLayout } from '@/components/layout/ConditionalLayout';
import { eurekaApi } from '@/lib/api/eureka';
import { ServiceTable } from '@/components/services/ServiceTable';
import { ServiceFilters } from '@/components/services/ServiceFilters';
import { Button } from '@/components/ui/button';
import { useToast } from '@/hooks/use-toast';

interface Filters {
  name: string;
  ip: string;
  status: string;
}

export default function ServicesPage() {
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const [filters, setFilters] = useState<Filters>({
    name: '',
    ip: '',
    status: '',
  });
  const [debouncedFilters, setDebouncedFilters] = useState<Filters>(filters);
  const [selected, setSelected] = useState<string[]>([]);

  // 防抖：输入停止300ms后才更新筛选条件
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedFilters(filters);
    }, 300);

    return () => clearTimeout(timer);
  }, [filters]);

  const { data, isLoading, refetch, error } = useQuery({
    queryKey: ['eureka-apps'],
    queryFn: eurekaApi.getApps,
    refetchInterval: 30000, // 每30秒自动刷新
  });

  const updateStatusMutation = useMutation({
    mutationFn: ({
      appName,
      instanceId,
      status,
    }: {
      appName: string;
      instanceId: string;
      status: 'UP' | 'OUT_OF_SERVICE';
    }) => eurekaApi.updateStatus(appName, instanceId, status),
    onSuccess: () => {
      toast({
        title: '操作成功',
        description: '实例状态已更新',
      });
      // 3秒后刷新数据
      setTimeout(() => {
        queryClient.invalidateQueries({ queryKey: ['eureka-apps'] });
      }, 3000);
    },
    onError: (error) => {
      toast({
        title: '操作失败',
        description: error instanceof Error ? error.message : '未知错误',
        variant: 'destructive',
      });
    },
  });

  // 展平数据结构: 从嵌套的 list[].instance[] 转为平铺的数组
  const flattenedData = useMemo(() => {
    if (!data?.list) return [];
    return data.list.flatMap((app) =>
      app.instance.map((inst) => ({
        ...inst,
        appName: app.name,
      }))
    );
  }, [data]);

  // 前端筛选 (使用防抖后的filters提升性能)
  const filteredData = useMemo(() => {
    return flattenedData.filter((row) => {
      if (debouncedFilters.name && row.appName !== debouncedFilters.name) return false;
      if (debouncedFilters.ip && row.ipAddr !== debouncedFilters.ip) return false;
      if (debouncedFilters.status && row.status !== debouncedFilters.status) return false;
      return true;
    });
  }, [flattenedData, debouncedFilters]);

  // 批量操作
  const handleBulkOperation = (operation: 'active' | 'inactive') => {
    if (selected.length === 0) {
      toast({
        title: '请先选择至少一项数据',
        variant: 'destructive',
      });
      return;
    }

    const status = operation === 'active' ? 'UP' : 'OUT_OF_SERVICE';
    const statusDesc = operation === 'active' ? '启用' : '禁用';

    if (!confirm(`确定要${statusDesc}选中的 ${selected.length} 个实例吗?`)) {
      return;
    }

    selected.forEach((instanceId) => {
      const instance = flattenedData.find((d) => d.instanceId === instanceId);
      if (instance) {
        updateStatusMutation.mutate({
          appName: instance.appName,
          instanceId,
          status,
        });
      }
    });

    setSelected([]);
  };

  return (
    <ConditionalLayout>
      <div>
        <div className="flex items-center justify-between mb-4 sm:mb-6">
          <h1 className="text-xl sm:text-2xl font-bold">Service Admin</h1>
        </div>

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded mb-4 text-sm">
            {error instanceof Error ? error.message : '未知错误'}
          </div>
        )}

        <ServiceFilters filters={filters} onChange={setFilters} data={flattenedData} />

        <div className="flex flex-wrap gap-2 my-4">
          <Button
            onClick={() => handleBulkOperation('active')}
            className="bg-green-600 hover:bg-green-700 text-sm"
            size="sm"
          >
            Active
          </Button>
          <Button
            onClick={() => handleBulkOperation('inactive')}
            variant="secondary"
            size="sm"
            className="text-sm"
          >
            Inactive
          </Button>
          <Button
            onClick={() => refetch()}
            variant="outline"
            size="sm"
            className="text-sm"
          >
            Refresh
          </Button>
        </div>

        <ServiceTable
          data={filteredData}
          selected={selected}
          onSelectionChange={setSelected}
          isLoading={isLoading}
        />
      </div>
    </ConditionalLayout>
  );
}
