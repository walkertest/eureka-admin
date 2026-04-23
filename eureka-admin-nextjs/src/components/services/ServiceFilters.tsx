'use client';

import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';

interface Filters {
  name: string;
  ip: string;
  status: string;
}

interface ServiceTableData {
  appName: string;
  ipAddr: string;
  [key: string]: any;
}

interface ServiceFiltersProps {
  filters: Filters;
  onChange: (filters: Filters) => void;
  data: ServiceTableData[];
}

export function ServiceFilters({ filters, onChange, data }: ServiceFiltersProps) {
  // 提取唯一的服务名称和IP地址用于自动建议
  const uniqueNames = Array.from(new Set(data.map((d) => d.appName))).sort();
  const uniqueIps = Array.from(new Set(data.map((d) => d.ipAddr))).sort();

  return (
    <div className="bg-white p-4 rounded-lg shadow">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <Label htmlFor="name">ServiceId</Label>
          <Input
            id="name"
            list="service-names"
            value={filters.name}
            onChange={(e) => onChange({ ...filters, name: e.target.value })}
            placeholder="输入服务ID"
          />
          <datalist id="service-names">
            {uniqueNames.map((name) => (
              <option key={name} value={name} />
            ))}
          </datalist>
        </div>
        <div>
          <Label htmlFor="ip">IP</Label>
          <Input
            id="ip"
            list="service-ips"
            value={filters.ip}
            onChange={(e) => onChange({ ...filters, ip: e.target.value })}
            placeholder="输入IP地址"
          />
          <datalist id="service-ips">
            {uniqueIps.map((ip) => (
              <option key={ip} value={ip} />
            ))}
          </datalist>
        </div>
        <div>
          <Label htmlFor="status">Status</Label>
          <Select
            value={filters.status}
            onValueChange={(value) => onChange({ ...filters, status: value })}
          >
            <SelectTrigger id="status">
              <SelectValue placeholder="选择状态" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="ALL">ALL</SelectItem>
              <SelectItem value="UP">UP</SelectItem>
              <SelectItem value="DOWN">DOWN</SelectItem>
              <SelectItem value="OUT_OF_SERVICE">OUT_OF_SERVICE</SelectItem>
            </SelectContent>
          </Select>
        </div>
      </div>
    </div>
  );
}
