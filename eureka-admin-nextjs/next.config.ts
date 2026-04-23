import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  // 静态导出模式 (仅在生产构建时启用)
  // ...(process.env.NODE_ENV === 'production' && { output: 'export' }),

  // 图片优化 (使用 unoptimized 因为静态导出不支持 Image Optimization API)
  images: {
    unoptimized: true,
  },

  // 禁用 trailing slashes (保持与旧版路由一致)
  trailingSlash: false,

  // 开发环境 API 代理
  async rewrites() {
    return process.env.NODE_ENV === 'development' ? [
      {
        source: '/eureka/:path*',
        destination: `${process.env.SPRING_BOOT_API || 'http://localhost:11111'}/eureka/:path*`,
      },
    ] : [];
  },
};

export default nextConfig;
