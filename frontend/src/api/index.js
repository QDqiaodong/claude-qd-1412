import axios from 'axios'

const http = axios.create({ baseURL: '/api', timeout: 10000 })
http.interceptors.response.use(
  response => response.data.data,
  error => Promise.reject(new Error(error.response?.data?.message || '请求失败'))
)

export const listRecords = (endpoint, params = {}) => http.get(endpoint, { params })
export const getRecord = (endpoint, id) => http.get(`${endpoint}/${id}`)
export const createRecord = (endpoint, data) => http.post(endpoint, data)
export const updateRecord = (endpoint, id, data) => http.put(`${endpoint}/${id}`, data)
export const listVehicles = () => http.get('/vehicles')
