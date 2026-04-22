import { AlertCircle } from 'lucide-react';
import { Button } from './button';

interface ErrorStateProps {
  title?: string;
  message: string;
  onRetry?: () => void;
}

export function ErrorState({
  title = '加载失败',
  message,
  onRetry
}: ErrorStateProps) {
  return (
    <div className="flex flex-col items-center justify-center py-12 px-4">
      <div className="rounded-full bg-red-100 p-6 mb-4">
        <AlertCircle className="h-12 w-12 text-red-600" />
      </div>
      <h3 className="text-lg font-semibold text-gray-900 mb-2">{title}</h3>
      <p className="text-sm text-gray-600 text-center max-w-md mb-6">
        {message}
      </p>
      {onRetry && (
        <Button onClick={onRetry} variant="outline">
          重试
        </Button>
      )}
    </div>
  );
}
