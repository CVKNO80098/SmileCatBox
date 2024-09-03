import { createRouter, createWebHistory } from 'vue-router';
import ViewPageVue from '../components/ViewPage.vue';
import PostDetailVue from '../components/PostDetail.vue'

const routes = [
  { path: '/detail', name: 'detail', component: ViewPageVue },
  { path: '/postDetail', name: 'postDetail', component: PostDetailVue }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
