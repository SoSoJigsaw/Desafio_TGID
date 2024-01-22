import { createRouter, createWebHistory } from 'vue-router'
import TransacoesVue from '@/views/Transacoes.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'transacoes',
      component: TransacoesVue
    },
    
    {
      path: '/tabelas',
      name: 'tabelas',
      component: () => import('../views/Tabelas.vue')
    },

    {
      path: '/cadastro',
      name: 'cadastro',
      component: () => import('../views/Cadastro.vue')
    },

  ]
})

export default router

