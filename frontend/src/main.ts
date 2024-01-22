import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import axios from 'axios';

const axiosInstance = axios.create({
    withCredentials: true,
    baseURL: 'http://127.0.0.1:5000',
  });

import './assets/main.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
