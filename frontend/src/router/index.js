import { createRouter, createWebHashHistory } from 'vue-router'
import VehiclesView from '../views/VehiclesView.vue'
import OrdersView from '../views/OrdersView.vue'
import AssembliesView from '../views/AssembliesView.vue'
import HazardousWasteView from '../views/HazardousWasteView.vue'
import SettlementsView from '../views/SettlementsView.vue'

export default createRouter({
  history: createWebHashHistory(),
  routes: [
    { path: '/', redirect: '/vehicles' },
    { path: '/vehicles', component: VehiclesView, meta: { title: '报废车辆台账' } },
    { path: '/orders', component: OrdersView, meta: { title: '拆解工单' } },
    { path: '/assemblies', component: AssembliesView, meta: { title: '五大总成台账' } },
    { path: '/hazardous-waste', component: HazardousWasteView, meta: { title: '危废暂存记录' } },
    { path: '/settlements', component: SettlementsView, meta: { title: '注销与结算' } }
  ]
})
