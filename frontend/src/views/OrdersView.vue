<script setup>
import CrudWorkspace from '../components/CrudWorkspace.vue'
const fields = [
  { key: 'orderNo', label: '工单号' }, { key: 'vehicleId', label: '关联车辆', type: 'vehicle' },
  { key: 'startTime', label: '开工时间', type: 'datetime' },
  { key: 'completionTime', label: '完工时间', type: 'datetime', optional: true },
  { key: 'teamName', label: '操作班组' }
]
</script>
<template><CrudWorkspace title="拆解工单" subtitle="按车辆关联记录拆解时间与作业班组" endpoint="/dismantling-orders"
  :fields="fields" primary-field="orderNo" secondary-field="vehiclePlate" /></template>
