'use client';

import { useCallback } from 'react';
import { formatDate } from '@/lib/utils/dateFormat';
import { Checkbox } from '@/components/ui/checkbox';
import { Badge } from '@/components/ui/badge';
import { Skeleton } from '@/components/ui/skeleton';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import type { EurekaInstance } from '@/types/eureka';

interface ServiceTableData extends EurekaInstance {
  appName: string;
}

interface ServiceTableProps {
  data: ServiceTableData[];
  selected: string[];
  onSelectionChange: (selected: string[]) => void;
  isLoading: boolean;
}

export function ServiceTable({
  data,
  selected,
  onSelectionChange,
  isLoading,
}: ServiceTableProps) {
  const handleSelectAll = useCallback((checked: boolean) => {
    if (checked) {
      onSelectionChange(data.map((d) => d.instanceId));
    } else {
      onSelectionChange([]);
    }
  }, [data, onSelectionChange]);

  const handleSelectOne = useCallback((instanceId: string, checked: boolean) => {
    if (checked) {
      onSelectionChange([...selected, instanceId]);
    } else {
      onSelectionChange(selected.filter((id) => id !== instanceId));
    }
  }, [selected, onSelectionChange]);

  if (isLoading) {
    return <ServiceTableSkeleton />;
  }

  if (data.length === 0) {
    return <div className="text-center py-8 text-gray-600">No Service Registry</div>;
  }

  return (
    <div className="bg-white rounded-lg shadow overflow-x-auto">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="w-12">
              <Checkbox
                checked={selected.length === data.length && data.length > 0}
                onCheckedChange={handleSelectAll}
              />
            </TableHead>
            <TableHead>ServiceId</TableHead>
            <TableHead>Status</TableHead>
            <TableHead>IP</TableHead>
            <TableHead>InstanceId</TableHead>
            <TableHead>Zone</TableHead>
            <TableHead>Register Time</TableHead>
            <TableHead>Enable Time</TableHead>
            <TableHead>Update Time</TableHead>
            <TableHead>Refresh Interval</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {data.map((row) => (
            <TableRow key={row.instanceId}>
              <TableCell>
                <Checkbox
                  checked={selected.includes(row.instanceId)}
                  onCheckedChange={(checked) =>
                    handleSelectOne(row.instanceId, checked as boolean)
                  }
                />
              </TableCell>
              <TableCell className="font-medium">{row.appName}</TableCell>
              <TableCell>
                <Badge
                  variant={row.status === 'UP' ? 'default' : 'destructive'}
                  className={
                    row.status === 'UP'
                      ? 'bg-green-600 hover:bg-green-700'
                      : ''
                  }
                >
                  {row.status}
                </Badge>
              </TableCell>
              <TableCell>{row.ipAddr}</TableCell>
              <TableCell className="text-xs max-w-xs truncate">
                {row.instanceId}
              </TableCell>
              <TableCell>{row.metadata?.zone || '-'}</TableCell>
              <TableCell>
                {formatDate(row.leaseInfo.registrationTimestamp)}
              </TableCell>
              <TableCell>
                {formatDate(row.leaseInfo.serviceUpTimestamp)}
              </TableCell>
              <TableCell>{formatDate(row.lastUpdatedTimestamp)}</TableCell>
              <TableCell>{row.leaseInfo.renewalIntervalInSecs}s</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}

function ServiceTableSkeleton() {
  return (
    <div className="bg-white rounded-lg shadow overflow-x-auto">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="w-12"><Skeleton className="h-4 w-4" /></TableHead>
            <TableHead><Skeleton className="h-4 w-20" /></TableHead>
            <TableHead><Skeleton className="h-4 w-16" /></TableHead>
            <TableHead><Skeleton className="h-4 w-20" /></TableHead>
            <TableHead><Skeleton className="h-4 w-24" /></TableHead>
            <TableHead><Skeleton className="h-4 w-16" /></TableHead>
            <TableHead><Skeleton className="h-4 w-28" /></TableHead>
            <TableHead><Skeleton className="h-4 w-24" /></TableHead>
            <TableHead><Skeleton className="h-4 w-24" /></TableHead>
            <TableHead><Skeleton className="h-4 w-28" /></TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {[...Array(5)].map((_, i) => (
            <TableRow key={i}>
              <TableCell><Skeleton className="h-4 w-4" /></TableCell>
              <TableCell><Skeleton className="h-4 w-32" /></TableCell>
              <TableCell><Skeleton className="h-6 w-16 rounded-full" /></TableCell>
              <TableCell><Skeleton className="h-4 w-24" /></TableCell>
              <TableCell><Skeleton className="h-4 w-48" /></TableCell>
              <TableCell><Skeleton className="h-4 w-20" /></TableCell>
              <TableCell><Skeleton className="h-4 w-36" /></TableCell>
              <TableCell><Skeleton className="h-4 w-36" /></TableCell>
              <TableCell><Skeleton className="h-4 w-36" /></TableCell>
              <TableCell><Skeleton className="h-4 w-12" /></TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}
