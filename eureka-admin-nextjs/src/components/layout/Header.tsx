'use client';

import { ClusterSelector } from '@/components/cluster/ClusterSelector';
import { EnvSelector } from '@/components/cluster/EnvSelector';
import { Menu } from 'lucide-react';
import { Button } from '@/components/ui/button';

interface HeaderProps {
  onMenuClick?: () => void;
}

export function Header({ onMenuClick }: HeaderProps) {
  return (
    <header className="bg-[#34302d] text-white px-4 sm:px-6 py-3 flex justify-between items-center">
      <div className="flex items-center gap-3">
        <Button
          variant="ghost"
          size="icon"
          className="md:hidden text-white hover:bg-[#4a4540]"
          onClick={onMenuClick}
        >
          <Menu className="h-5 w-5" />
        </Button>
        <img src="/images/nav.png" alt="Logo" className="h-6 sm:h-8" />
        <span className="text-sm sm:text-base hidden sm:inline">运营平台技术部</span>
      </div>
      <div className="flex gap-2 sm:gap-4 text-xs sm:text-sm">
        <ClusterSelector />
        <EnvSelector />
      </div>
    </header>
  );
}
