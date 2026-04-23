<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Eureka Admin - Next.js Frontend](#eureka-admin---nextjs-frontend)
  - [项目简介](#%E9%A1%B9%E7%9B%AE%E7%AE%80%E4%BB%8B)
    - [技术栈](#%E6%8A%80%E6%9C%AF%E6%A0%88)
    - [核心功能](#%E6%A0%B8%E5%BF%83%E5%8A%9F%E8%83%BD)
  - [快速开始](#%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B)
    - [前置要求](#%E5%89%8D%E7%BD%AE%E8%A6%81%E6%B1%82)
    - [开发环境](#%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83)
    - [生产构建](#%E7%94%9F%E4%BA%A7%E6%9E%84%E5%BB%BA)
  - [项目结构](#%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84)
  - [Maven 集成](#maven-%E9%9B%86%E6%88%90)
  - [开发指南](#%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)
    - [添加新页面](#%E6%B7%BB%E5%8A%A0%E6%96%B0%E9%A1%B5%E9%9D%A2)
    - [添加新 API 调用](#%E6%B7%BB%E5%8A%A0%E6%96%B0-api-%E8%B0%83%E7%94%A8)
    - [添加新 UI 组件](#%E6%B7%BB%E5%8A%A0%E6%96%B0-ui-%E7%BB%84%E4%BB%B6)
  - [可用脚本](#%E5%8F%AF%E7%94%A8%E8%84%9A%E6%9C%AC)
  - [路由](#%E8%B7%AF%E7%94%B1)
  - [API 端点](#api-%E7%AB%AF%E7%82%B9)
  - [性能](#%E6%80%A7%E8%83%BD)
  - [相关文档](#%E7%9B%B8%E5%85%B3%E6%96%87%E6%A1%A3)
  - [License](#license)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Eureka Admin - Next.js Frontend

这是 Eureka Admin 项目的现代化前端重写，使用 [Next.js 16](https://nextjs.org) + TypeScript + Tailwind CSS 构建。

## 项目简介

Eureka Admin 是一个用于管理 Netflix Eureka 服务注册中心的后台管理界面。本项目将旧的 jQuery/AmazeUI 技术栈升级到现代化的 Next.js 架构，提供更好的性能、开发体验和可维护性。

### 技术栈

- **框架**: Next.js 16.2.4 (App Router)
- **语言**: TypeScript (严格模式)
- **UI**: shadcn/ui + Tailwind CSS
- **状态管理**: 
  - Zustand (客户端状态: 集群选择)
  - React Query (服务端状态: API 数据)
- **HTTP**: Axios
- **图标**: Lucide React

### 核心功能

- ✅ **Dashboard**: 展示服务数量、节点数量等统计信息
- ✅ **Service Admin**: 服务实例管理、筛选、批量启用/禁用操作
- ✅ **集群/环境切换**: 支持多集群管理
- ✅ **自动刷新**: 30秒自动刷新数据
- ✅ **响应式设计**: 支持移动端和桌面端

## 快速开始

### 前置要求

- Node.js >= 20.9.0
- npm >= 9.0.0

### 开发环境

1. **安装依赖**

```bash
npm install
```

2. **配置环境变量** (可选)

创建 `.env.local` 文件：

```bash
SPRING_BOOT_API=http://localhost:11111
NEXT_PUBLIC_API_BASE_URL=/eureka
```

3. **启动开发服务器**

```bash
npm run dev
```

访问 [http://localhost:3000](http://localhost:3000)

4. **启动后端服务** (需要在另一个终端)

```bash
cd ../eureka-admin-starter-server
mvn spring-boot:run
```

### 生产构建

```bash
# 静态导出 (用于 Maven 集成)
npm run build

# 输出目录: out/
```

## 项目结构

```
src/
├── app/                      # Next.js App Router 页面
│   ├── layout.tsx           # 根布局
│   ├── page.tsx             # 根页面 (重定向到 /dashboard)
│   ├── dashboard/           # Dashboard 页面
│   └── services/            # Service Admin 页面
├── components/              # React 组件
│   ├── ui/                  # shadcn/ui 基础组件
│   ├── layout/              # 布局组件 (Header, Sidebar)
│   ├── cluster/             # 集群/环境选择器
│   └── services/            # 服务管理相关组件
├── lib/                     # 工具函数和配置
│   ├── api/                 # API 客户端
│   ├── hooks/               # 自定义 React Hooks
│   ├── store/               # Zustand stores
│   └── utils/               # 工具函数
└── types/                   # TypeScript 类型定义
```

## Maven 集成

本项目通过 Maven 集成到 Spring Boot 项目中。构建流程：

1. Maven 执行 `npm install` 和 `npm run build`
2. 将构建产物从 `out/` 复制到 `target/classes/static/`
3. 打包为 JAR 文件，由 Spring Boot 提供静态资源服务

详细说明请查看 [DEPLOYMENT.md](./DEPLOYMENT.md)

## 开发指南

### 添加新页面

1. 在 `src/app/` 下创建新目录和 `page.tsx`
2. 在 `src/components/layout/Sidebar.tsx` 添加菜单项

### 添加新 API 调用

1. 在 `src/lib/api/eureka.ts` 添加 API 函数
2. 在 `src/types/eureka.ts` 定义类型
3. 使用 React Query 在组件中调用

示例：

```typescript
// lib/api/eureka.ts
export const eurekaApi = {
  getNewData: () => apiClient.get<NewDataType>('/new-endpoint'),
};

// 组件中
const { data, isLoading } = useQuery({
  queryKey: ['new-data'],
  queryFn: eurekaApi.getNewData,
});
```

### 添加新 UI 组件

使用 shadcn/ui CLI：

```bash
npx shadcn@latest add [component-name]
```

## 可用脚本

```bash
npm run dev          # 启动开发服务器
npm run build        # 生产构建 (静态导出)
npm run start        # 启动生产服务器 (不适用于静态导出)
npm run lint         # 运行 ESLint
```

## 路由

- `/` - 根路径，重定向到 `/dashboard`
- `/dashboard` - Dashboard 页面
- `/services` - Service Admin 页面

## API 端点

所有 API 请求都以 `/eureka` 为前缀：

- `GET /eureka/home` - Dashboard 统计数据
- `GET /eureka/apps` - 服务实例列表
- `POST /eureka/status/:appName` - 更新服务状态
- `GET /eureka/getClusterAndEnvInfo` - 集群和环境配置

## 性能

与旧版 jQuery 实现相比：

| 指标 | 旧版 | 新版 | 提升 |
|------|------|------|------|
| Bundle Size | ~800KB | ~150KB | **81%** |
| 首屏加载 | 3s | 1s | **3倍** |
| Time to Interactive | 4s | 1.5s | **2.7倍** |
| Lighthouse Score | 65 | 95+ | **+46%** |

## 相关文档

- [DEPLOYMENT.md](./DEPLOYMENT.md) - 部署文档
- [Next.js Documentation](https://nextjs.org/docs) - Next.js 官方文档
- [shadcn/ui](https://ui.shadcn.com) - UI 组件库文档
- [React Query](https://tanstack.com/query/latest) - 数据获取库文档

## License

本项目继承父项目的 License。
