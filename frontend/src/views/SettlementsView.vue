<script setup>
import CrudWorkspace from '../components/CrudWorkspace.vue'
const statuses = ['待办理', '办理中', '已注销']
const fields = [
  { key: 'vehicleId', label: '车辆', type: 'vehicle' },
  { key: 'cancellationStatus', label: '注销状态', type: 'select', options: statuses },
  { key: 'certificateNo', label: '注销证明编号' },
  { key: 'residualValue', label: '残值金额', type: 'money' },
  { key: 'processingFee', label: '处理费', type: 'money' },
  { key: 'paidAmount', label: '实付金额', type: 'money' }
]
</script>
<template><CrudWorkspace title="注销与结算" subtitle="统一记录车辆注销凭证与结算金额" endpoint="/cancellation-settlements"
  :fields="fields" primary-field="certificateNo" secondary-field="vehiclePlate"
  :filter-field="{ key:'cancellationStatus', label:'注销状态', options:statuses }" /></template>
