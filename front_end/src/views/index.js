import { createWebHistory, createRouter } from 'vue-router';
import endPoint from '@/cfg/endPoint';

const routes = [
  { path: endPoint.home, component: () => import("@/views/view_home.vue")},
  { path: endPoint.login, component: () => import("@/views/view_login.vue")},
  { path: endPoint.join, component: () => import("@/views/view_join.vue")},
  { path: endPoint.search, component: () => import("@/views/view_search.vue")},
  { path: endPoint.item(":iid"), component: () => import("@/views/view_item.vue")},
  { path: endPoint.bucket, component: () => import("@/views/view_bucket.vue")},
  { path: endPoint.myPage, component: () => import("@/views/view_account.vue")},
  { path: endPoint.exchange, component: () => import("@/views/view_exchange.vue")},
  { path: endPoint.historyQuery, component: () => import("@/views/view_history_query.vue")},
  { path: endPoint.payment, component: () => import("@/views/view_payment.vue")},
  { path: endPoint.updateAccount, component: () => import("@/views/view_updateAccount.vue")},
];

export default createRouter({
  history: createWebHistory(),
  routes,
});