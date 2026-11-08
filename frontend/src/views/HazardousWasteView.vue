<script setup>
import CrudWorkspace from '../components/CrudWorkspace.vue'
const types = ['废机油', '废电瓶', '废三元催化', '废冷媒']
const fields = [
  { key: 'wasteType', label: '危废类型', type: 'select', options: types },
  { key: 'containerNo', label: '容器编号' }, { key: 'quantityKg', label: '数量', type: 'number', unit: ' kg' },
  { key: 'storageTime', label: '入库时间', type: 'datetime' },
  { key: 'vehicleId', label: '来源车辆（可选）', type: 'vehicle', optional: true }
]
</script>
<template><CrudWorkspace title="危废暂存记录" subtitle="查看危废入库批次、容器与可选车辆来源" endpoint="/hazardous-waste-records"
  :fields="fields" primary-field="containerNo" secondary-field="wasteType"
  :filter-field="{ key:'wasteType', label:'危废类型', options:types }" /></template>
