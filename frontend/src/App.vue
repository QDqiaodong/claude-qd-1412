<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const pageTitle = computed(() => route.meta.title || '业务看板')
</script>

<template>
  <div class="app-shell">
    <aside class="sidebar">
      <div class="brand">
        <div class="brand-mark">REC</div>
        <div>
          <div class="brand-title">报废汽车拆解厂</div>
          <div class="brand-subtitle">RESOURCE CONTROL</div>
        </div>
      </div>
      <el-menu class="side-menu" router :default-active="route.path">
        <el-menu-item index="/vehicles">报废车辆台账</el-menu-item>
        <el-menu-item index="/orders">拆解工单</el-menu-item>
        <el-menu-item index="/assemblies">五大总成台账</el-menu-item>
        <el-menu-item index="/hazardous-waste">危废暂存记录</el-menu-item>
        <el-menu-item index="/settlements">注销与结算</el-menu-item>
      </el-menu>
      <div class="side-footer">资源流向可追溯 · 拆解记录可核查</div>
    </aside>
    <main class="workspace">
      <header class="topbar">
        <div><div class="top-kicker">业务作业台 / {{ pageTitle }}</div><h1>{{ pageTitle }}</h1></div>
        <div class="top-status"><span></span>系统运行正常</div>
      </header>
      <section class="content"><router-view /></section>
    </main>
  </div>
</template>
