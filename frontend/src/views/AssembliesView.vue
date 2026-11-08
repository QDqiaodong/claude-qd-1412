<script setup>
import CrudWorkspace from '../components/CrudWorkspace.vue'
const types = ['发动机', '变速器', '方向机', '前后桥', '车架']
const fields = [
  { key: 'assemblyType', label: '总成类型', type: 'select', options: types },
  { key: 'assemblyNo', label: '总成编号' }, { key: 'vehicleId', label: '所属车辆', type: 'vehicle' },
  { key: 'destination', label: '去向' }
]
</script>
<template><CrudWorkspace title="五大总成台账" subtitle="记录总成身份、所属车辆与当前去向" endpoint="/assemblies"
  :fields="fields" primary-field="assemblyNo" secondary-field="vehiclePlate"
  :filter-field="{ key:'assemblyType', label:'总成类型', options:types }" /></template>
