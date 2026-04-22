'use client';

import { useQuery } from '@tanstack/react-query';
import { ConditionalLayout } from '@/components/layout/ConditionalLayout';
import { eurekaApi } from '@/lib/api/eureka';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';
import { ErrorState } from '@/components/ui/error-state';
import { Server, Activity, CheckCircle, ExternalLink } from 'lucide-react';

export default function DashboardPage() {
  const { data, isLoading, error, refetch } = useQuery({
    queryKey: ['dashboard'],
    queryFn: eurekaApi.getHomeMetrics,
  });

  return (
    <ConditionalLayout>
      <div>
        <h1 className="text-xl sm:text-2xl font-bold mb-4 sm:mb-6">Dashboard</h1>

        {isLoading && <DashboardSkeleton />}

        {error && (
          <ErrorState
            message={error instanceof Error ? error.message : '未知错误'}
            onRetry={() => refetch()}
          />
        )}

        {data && (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
            <MetricCard
              title="Service Count"
              value={data.appCount}
              icon={Server}
              color="blue"
            />
            <MetricCard
              title="Node Count"
              value={data.nodeCount}
              icon={Activity}
              color="purple"
            />
            <MetricCard
              title="Available Nodes"
              value={data.enableNodeCount}
              icon={CheckCircle}
              color="green"
            />
            <Card>
              <CardHeader>
                <CardTitle className="text-sm">Eureka Self Link</CardTitle>
              </CardHeader>
              <CardContent>
                <a
                  href={data.eurekaSelfLink}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center gap-2 text-blue-600 hover:underline text-sm break-all"
                >
                  Visit <ExternalLink className="w-4 h-4 flex-shrink-0" />
                </a>
              </CardContent>
            </Card>
          </div>
        )}
      </div>
    </ConditionalLayout>
  );
}

function DashboardSkeleton() {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
      {[...Array(4)].map((_, i) => (
        <Card key={i}>
          <CardHeader className="flex flex-row items-center justify-between pb-2">
            <Skeleton className="h-4 w-24" />
            <Skeleton className="h-5 w-5 rounded-full" />
          </CardHeader>
          <CardContent>
            <Skeleton className="h-9 w-16" />
          </CardContent>
        </Card>
      ))}
    </div>
  );
}

interface MetricCardProps {
  title: string;
  value: number | undefined;
  icon: React.ComponentType<{ className?: string }>;
  color: 'blue' | 'purple' | 'green';
}

function MetricCard({ title, value, icon: Icon, color }: MetricCardProps) {
  const colorClasses = {
    blue: 'text-blue-600',
    purple: 'text-purple-600',
    green: 'text-green-600',
  };

  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between pb-2">
        <CardTitle className="text-sm font-medium">{title}</CardTitle>
        <Icon className={`w-5 h-5 ${colorClasses[color]}`} />
      </CardHeader>
      <CardContent>
        <div className="text-3xl font-bold">{value ?? '-'}</div>
      </CardContent>
    </Card>
  );
}
