<template>

  <el-row>
    <el-button type="primary" @click="handleAdd">新增</el-button>
    <el-button type="primary" @click="handleTemplateAdd">按模板新增</el-button>
    <el-col :span="24">
      <div class="grid-content bg-purple-dark">
        <!--      表格 start-->
        <el-table
            :data="tableData"
            style="width: 100%">
          <el-table-column label="创建日期" width="180">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon></el-icon>
                <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="服务名称" width="180">
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <template #default>
                  <div>名称: {{ scope.row.serviceName }}</div>
                  <div>地址: {{ scope.row.serviceUrl }}</div>
                </template>
                <template #reference>
                  <el-tag>{{ scope.row.serviceName }}</el-tag>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="环境名称" width="120">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon></el-icon>
                <span style="margin-left: 10px">{{ scope.row.serviceEnvironment }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="被通知手机号" width="150">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon></el-icon>
                <span style="margin-left: 10px">{{ scope.row.notifyMobiles }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="钉钉群名称" width="220">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon></el-icon>
                <span style="margin-left: 10px">{{ scope.row.webhookGroupName }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="监控间隔" width="180">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon></el-icon>
                <span style="margin-left: 10px">{{ scope.row.cron }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="超时时长（ms）" width="180">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon></el-icon>
                <span style="margin-left: 10px">{{ scope.row.timeout }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="启用状态">
            <template #default="scope">
              <el-switch
                  v-model="scope.row.enableStatus"
                  :active-value="1"
                  :inactive-value="0"
                  inline-prompt
                  active-text="已启用"
                  inactive-text="已停用"
                  @change="handleEnableStatus(scope.row.enableStatus,scope.row.id)"
              />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button>
              <el-button
                  size="small"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-col>
  </el-row>

  <el-dialog
      v-model="dialogVisible"
      :title="editFormData.id?'修改监控项':'新增监控项'"
      :before-close="handleClose">
    <el-form
        ref="editFormRef"
        :model="editFormData"
        :rules="rules"
        label-width="120px"
        class="demo-ruleForm"
        :size="editFormSize"
        status-icon
    >
      <el-form-item label="服务名称" prop="serviceName">
        <el-input v-model="editFormData.serviceName" />
      </el-form-item>
      <el-form-item label="服务URL" prop="serviceUrl">
        <el-input v-model="editFormData.serviceUrl" />
      </el-form-item>
      <el-form-item label="环境名称" prop="serviceEnvironment">
        <el-select v-model="editFormData.serviceEnvironment" placeholder="环境名称">
          <el-option label="开发站" value="dev" />
          <el-option label="测试站" value="test" />
          <el-option label="正式站" value="prod" />
        </el-select>
      </el-form-item>
      <el-form-item label="通知邮箱" prop="notifyEmails">
        <el-input v-model="editFormData.notifyEmails" />
      </el-form-item>
      <el-form-item label="通知手机号" prop="notifyMobiles">
        <el-input v-model="editFormData.notifyMobiles" />
      </el-form-item>
      <el-form-item label="钉钉接口地址" prop="webhookUrl">
        <el-input v-model="editFormData.webhookUrl" />
      </el-form-item>
      <el-form-item label="钉钉关键词" prop="webhookKeyword">
        <el-input v-model="editFormData.webhookKeyword" />
      </el-form-item>
      <el-form-item label="钉钉群名称" prop="webhookGroupName">
        <el-input v-model="editFormData.webhookGroupName" />
      </el-form-item>
      <el-form-item label="cron表达式" prop="cron">
        <el-input v-model="editFormData.cron" />
      </el-form-item>
      <el-form-item label="监控超时时长" prop="timeout">
        <el-input v-model="editFormData.timeout" />
      </el-form-item>
      <el-form-item label="网络环境" prop="network">
        <el-input v-model="editFormData.network" />
      </el-form-item>
      <el-form-item label="是否启用" prop="enableStatus">
        <el-switch v-model="editFormData.enableStatus" :active-value="1" :inactive-value="0"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(editFormRef)">
          保存
        </el-button>
        <el-button @click="resetForm(editFormRef)">重置</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

<!--  <el-dialog v-model="dialogVisible" title="新增或者修改">-->
<!--1111 -->
<!--  </el-dialog>-->

</template>


<script setup lang="ts">
import { list,edit,deleteMonitor,enable,disable} from "../../api/monitor";
import {onMounted, reactive, ref} from "vue";
import {ElMessage, FormInstance, FormRules} from "element-plus";

let dialogVisible = ref(false)
const tableData = ref<any>([]);

async function getList() {
  let params = {
  };
  let res: any = await list(params);

  if (res.code === "1") {
    tableData.value = res?.data.list
  } else {
    ElMessage.error(res.message)
  }
}

async function doEdit(params:any) {

  let res: any = await edit(params);
  if (res.code === "1") {
    ElMessage.success(res.message)
  } else {
    ElMessage.error(res.message)
  }
}

async function doDeleteMonitor(params:any) {

  let res: any = await deleteMonitor(params);
  if (res.code === "1") {
    ElMessage.success(res.message)
    refreshParent();
  } else {
    ElMessage.error(res.message)
  }
}

async function handleEnableStatus(enableStatus:any,rowid:any) {
  var idsArray:string[];
  idsArray = [rowid];

  let ids :any={
    ids:idsArray
  };

  if(true == enableStatus){
    await enable(ids);
  }else{
    await disable(ids);
  }
}

async function refreshParent() {
  parent.location.reload();
}

onMounted(() => {
  getList();
});

interface MonitorConfigData {
  id: string
  serviceName: string
  serviceUrl: string
  serviceEnvironment: string
  notifyEmails: string
  notifyMobiles: string
  webhookUrl: string
  webhookKeyword: string
  webhookGroupName: string
  cron: string
  timeout: string
  network: string
  enableStatus: string
}

let blankMonitorConfigData = {
  id: '',
  serviceName: '',
  serviceUrl:  '',
  serviceEnvironment: '',
  notifyEmails: '',
  notifyMobiles: '',
  webhookUrl: '',
  webhookKeyword: '',
  webhookGroupName: '',
  cron: '',
  timeout: '',
  network: '',
  enableStatus: ''
}

let templateMonitorConfigData = {
  id: '',
  serviceName: '百度（baidu）',
  serviceUrl:  'http://www.baidu.com',
  serviceEnvironment: 'dev',
  notifyEmails: 'lmyanglei@163.com',
  notifyMobiles: '1380138000',
  webhookUrl: 'https://oapi.dingtalk.com/robot/send?access_token=420b1a020042e957f94705830dda5ce247efa4f82686f3435b576e22d956f800',
  webhookKeyword: '服务监控',
  webhookGroupName: '自己测试',
  cron: '0 0/10 * * * ? ',
  timeout: '10000',
  network: 'inner',
  enableStatus: '0'
}

const handleAdd = () => {
  Object.assign(editFormData,blankMonitorConfigData)
  editFormRef.value?.clearValidate;
  dialogVisible.value = true;
}

const handleTemplateAdd = () => {
  Object.assign(editFormData,templateMonitorConfigData)
  editFormRef.value?.clearValidate;
  dialogVisible.value = true;
}

const handleEdit = (index: number, row: MonitorConfigData) => {
  Object.assign(editFormData,row)
  editFormRef.value?.clearValidate;
  dialogVisible.value = true;
}
const handleDelete = (index: number, row: MonitorConfigData) => {
  var idsArray:string[];
  idsArray = [row.id]

  let ids :any={
    ids:idsArray
  };

  doDeleteMonitor(ids);
}

// const clearFormData = () => {
//   editFormData.id='';
//   editFormData.serviceName='';
//   editFormData.serviceUrl= '';
//   editFormData.serviceEnvironment='';
//   editFormData.notifyEmails='';
//   editFormData.notifyMobiles='';
//   editFormData.webhookUrl='';
//   editFormData.webhookKeyword='';
//   editFormData.webhookGroupName='';
//   editFormData.cron='';
//   editFormData.timeout='';
//   editFormData.network='';
//   editFormData.enableStatus=''
// }

const editFormSize = ref('default')
const editFormRef = ref<FormInstance>()
let editFormData = reactive({
  id: '',
  serviceName: '',
  serviceUrl: '',
  serviceEnvironment: '',
  notifyEmails: '',
  notifyMobiles: '',
  webhookUrl: '',
  webhookKeyword: '',
  webhookGroupName: '',
  cron: '',
  timeout: '',
  network: '',
  enableStatus: '',
})

const rules = reactive<FormRules>({
  serviceName: [
    { required: true, message: '请输入服务名称', trigger: 'change' },
    { min: 1, max: 100, message: '长度为1至100', trigger: 'change' },
  ],
  serviceUrl: [
    {
      required: true,
      message: '请输入要监控的服务URL',
      trigger: 'change',
    },
  ],
  serviceEnvironment: [
    {
      required: true,
      message: '请选择环境名称',
      trigger: 'change',
    },
  ],
  notifyEmails: [
    {
      required: true,
      message: '请输入要要通知的邮箱',
      trigger: 'change',
    },
  ],
  notifyMobiles: [
    {
      required: true,
      message: '请输入要要通知的手机号',
      trigger: 'change',
    },
  ],
  webhookUrl: [
    {
      required: true,
      message: '请输入钉钉接口地址',
      trigger: 'change',
    },
  ],
  webhookKeyword: [
    {
      required: true,
      message: '请输入钉钉关键词',
      trigger: 'change',
    },
  ],
  webhookGroupName: [
    {
      required: true,
      message: '请输入钉钉群名称',
      trigger: 'change',
    },
  ],
  cron: [
    {
      required: true,
      message: '请输入cron表达式',
      trigger: 'change',
    },
  ],
  timeout: [
    {
      required: true,
      message: '请输入监控超时时长',
      trigger: 'change',
    },
  ],
  network: [
    {
      required: true,
      message: '请输入网络环境',
      trigger: 'change',
    },
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      doEdit(JSON.stringify(editFormData))
      dialogVisible.value=false;
      refreshParent();
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const handleClose = (done: () => void) => {
  // Object.assign(editFormData,blankMonitorConfigData);
  // editFormData.serviceName='';
  // editFormRef.value?.resetFields;
  // editFormRef.value?.clearValidate;
  done();
}

</script>

<style>
.el-row {
  margin-bottom: 20px;
&:last-child {
   margin-bottom: 0;
 }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #fdfdfd;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}

.el-button--text {
  margin-right: 15px;
}
.el-select {
  width: 300px;
}
.el-input {
  width: 300px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}

</style>

