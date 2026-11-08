<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { createRecord, getRecord, listRecords, listVehicles, updateRecord } from '../api'

const props = defineProps({
  title: String, subtitle: String, endpoint: String, fields: Array,
  primaryField: String, secondaryField: String, filterField: Object
})
const rows = ref([])
const vehicles = ref([])
const selected = ref(null)
const loading = ref(false)
const drawer = ref(false)
const editing = ref(false)
const keyword = ref('')
const filterValue = ref('')
const form = reactive({})
const visibleFields = computed(() => props.fields.filter(field => !field.hiddenInDetail))

async function load() {
  loading.value = true
  try {
    const params = { keyword: keyword.value || undefined }
    if (props.filterField && filterValue.value) params[props.filterField.key] = filterValue.value
    rows.value = await listRecords(props.endpoint, params)
    if (selected.value) {
      const same = rows.value.find(item => item.id === selected.value.id)
      selected.value = same ? await getRecord(props.endpoint, same.id) : null
    }
    if (!selected.value && rows.value.length) selected.value = await getRecord(props.endpoint, rows.value[0].id)
  } catch (error) { ElMessage.error(error.message) }
  finally { loading.value = false }
}

async function selectRow(row) {
  try { selected.value = await getRecord(props.endpoint, row.id) }
  catch (error) { ElMessage.error(error.message) }
}

function resetForm() {
  Object.keys(form).forEach(key => delete form[key])
  props.fields.forEach(field => { form[field.key] = field.type === 'number' || field.type === 'money' ? null : '' })
}

function beginCreate() { editing.value = false; resetForm(); drawer.value = true }
function beginEdit(row = selected.value) {
  if (!row) return
  editing.value = true
  resetForm()
  props.fields.forEach(field => { form[field.key] = row[field.key] ?? (field.type === 'number' || field.type === 'money' ? null : '') })
  form.id = row.id
  drawer.value = true
}

async function save() {
  try {
    const payload = {}
    props.fields.forEach(field => { payload[field.key] = field.optional && form[field.key] === '' ? null : form[field.key] })
    const saved = editing.value
      ? await updateRecord(props.endpoint, form.id, payload)
      : await createRecord(props.endpoint, payload)
    drawer.value = false
    selected.value = saved
    await load()
    ElMessage.success(editing.value ? '记录已更新' : '记录已新增')
  } catch (error) { ElMessage.error(error.message) }
}

function vehicleLabel(id) { return vehicles.value.find(item => item.id === id)?.plateNumber || `车辆 #${id}` }
function display(field, value) {
  if (value === null || value === undefined || value === '') return '—'
  if (field.type === 'money') return `¥ ${Number(value).toFixed(2)}`
  if (field.type === 'number') return `${value}${field.unit || ''}`
  if (field.type === 'vehicle') return selected.value?.vehiclePlate || vehicleLabel(value)
  return value
}

watch(() => props.endpoint, load)
onMounted(async () => {
  try { vehicles.value = await listVehicles() } catch (error) { ElMessage.error(error.message) }
  await load()
})
</script>

<template>
  <div class="module-head">
    <div><h2>{{ title }}</h2><p>{{ subtitle }}</p></div>
    <el-button type="primary" @click="beginCreate">新增记录</el-button>
  </div>
  <div class="filter-strip">
    <el-input v-model="keyword" clearable placeholder="输入编号、车牌或关键字" @keyup.enter="load" />
    <el-select v-if="filterField" v-model="filterValue" clearable :placeholder="filterField.label" @change="load">
      <el-option v-for="option in filterField.options" :key="option" :label="option" :value="option" />
    </el-select>
    <el-button @click="load">筛选</el-button>
    <span class="result-count">{{ rows.length }} 条记录</span>
  </div>
  <div class="master-detail" v-loading="loading">
    <section class="record-list">
      <button v-for="row in rows" :key="row.id" class="record-item"
        :class="{ active: selected?.id === row.id }" @click="selectRow(row)" @dblclick="beginEdit(row)">
        <span class="record-code">{{ row[primaryField] }}</span>
        <span class="record-meta">{{ row[secondaryField] || row.vehiclePlate || `#${row.id}` }}</span>
      </button>
      <el-empty v-if="!rows.length" description="暂无匹配记录" :image-size="72" />
    </section>
    <section class="detail-pane">
      <template v-if="selected">
        <div class="detail-heading">
          <div><span class="detail-eyebrow">记录详情</span><h3>{{ selected[primaryField] }}</h3></div>
          <el-button @click="beginEdit()">编辑</el-button>
        </div>
        <dl class="detail-grid">
          <template v-for="field in visibleFields" :key="field.key">
            <dt>{{ field.label }}</dt><dd>{{ display(field, selected[field.key]) }}</dd>
          </template>
        </dl>
      </template>
      <el-empty v-else description="从左侧选择一条记录" />
    </section>
  </div>

  <el-drawer v-model="drawer" :title="editing ? `编辑${title}` : `新增${title}`" size="480px">
    <el-form label-position="top">
      <el-form-item v-for="field in fields" :key="field.key" :label="field.label">
        <el-select v-if="field.type === 'select'" v-model="form[field.key]" style="width:100%">
          <el-option v-for="option in field.options" :key="option" :label="option" :value="option" />
        </el-select>
        <el-select v-else-if="field.type === 'vehicle'" v-model="form[field.key]" clearable style="width:100%">
          <el-option v-for="vehicle in vehicles" :key="vehicle.id" :label="`${vehicle.plateNumber} · ${vehicle.brandModel}`" :value="vehicle.id" />
        </el-select>
        <el-date-picker v-else-if="field.type === 'date'" v-model="form[field.key]" type="date" value-format="YYYY-MM-DD" style="width:100%" />
        <el-date-picker v-else-if="field.type === 'datetime'" v-model="form[field.key]" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" style="width:100%" />
        <el-input-number v-else-if="field.type === 'number' || field.type === 'money'" v-model="form[field.key]"
          :precision="2" :min="0" controls-position="right" style="width:100%" />
        <el-input v-else v-model="form[field.key]" />
      </el-form-item>
    </el-form>
    <template #footer><el-button @click="drawer=false">取消</el-button><el-button type="primary" @click="save">保存</el-button></template>
  </el-drawer>
</template>
