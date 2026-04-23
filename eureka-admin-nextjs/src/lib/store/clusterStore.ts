import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { setCookie } from '@/lib/utils/cookies';
import type { ClusterInfo, EnvInfo } from '@/types/eureka';

interface ClusterState {
  currentCluster: ClusterInfo | null;
  currentEnv: string | null;
  currentEnvInfo: EnvInfo | null;
  setCluster: (cluster: ClusterInfo) => void;
  setEnv: (env: EnvInfo) => void;
}

export const useClusterStore = create<ClusterState>()(
    (set) => ({
      currentCluster: null,
      currentEnv: null,
      currentEnvInfo: null,

      setCluster: (cluster) => {
        setCookie('eureka_cluster', cluster.name, 30);
        set({ currentCluster: cluster });
      },

      setEnv: (env) => {
        console.log("Setting environment:", env);
        //新打开标签，跳转到url
        window.open(env.url, '_blank');

        // set({ currentEnv: env.name});
        // set({currentEnvInfo: env})
      },
    })
);
