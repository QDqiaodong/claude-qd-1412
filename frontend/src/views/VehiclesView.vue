<script setup>
import CrudWorkspace from '../components/CrudWorkspace.vue'
const fields = [
  { key: 'plateNumber', label: '车牌号' }, { key: 'vin', label: '车架号' },
  { key: 'brandModel', label: '品牌型号' }, { key: 'entryDate', label: '进场日期', type: 'date' },
  { key: 'sourceType', label: '来源类型', type: 'select', options: ['个人', '单位', '保险', '执法'] },
  { key: 'status', label: '当前状态', type: 'select', options: ['已进场', '拆解中', '已拆解', '已注销'] }
]
</script>
<template><CrudWorkspace title="报废车辆台账" subtitle="集中查看车辆身份、来源与当前进度" endpoint="/vehicles"
  :fields="fields" primary-field="plateNumber" secondary-field="brandModel"
  :filter-field="{ key:'status', label:'车辆状态', options:['已进场','拆解中','已拆解','已注销'] }" /></template>
