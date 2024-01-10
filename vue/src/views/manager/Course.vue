<template>
  <div>
    <div class="card searchbox">
      <el-input style="width: 250px;" v-model="data.name" class="w-50 m-2" placeholder="請輸入課程名稱" :prefix-icon="Search" />
      <el-input style="width: 250px;margin-left:10px;" v-model="data.courseNo" class="w-50 m-2" placeholder="請輸入課程編號" :prefix-icon="Search" />
      <el-button type="primary" style="margin-left:10px" @click="searchList">查詢</el-button>
      <el-button type="warning" @click="resetSearch">重置</el-button>
    </div>

    <div class="card">
      <el-button type="primary" style="margin-left:10px" @click="addCourse">新增</el-button>
      <el-table :data="data.tableData" style="width: 100%">
          <el-table-column prop="id" label="序號"  width="70" />
          <el-table-column prop="name" label="名稱"  />
          <el-table-column prop="courseNo" label="課程編號"  />
          <el-table-column prop="times" label="課時"  />
          <el-table-column prop="teacher" label="任課老師"  />

          <el-table-column label="Operations">
              <template #default="scope">
                <el-button size="small" @click="courseEdit(scope.$index, scope.row)" >編輯</el-button>
                <el-button size="small" type="danger" @click="courseDelete(scope.$index, scope.row)" >刪除</el-button>
              </template>
          </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize" 
            @current-change="handleCurrentChange"
            background layout="prev,pager,next" :total="data.total" />
    </div>

    <!--新增-->
    <el-dialog title="新增課程信息"  v-model="data.dialogFormVisible">
      <el-form :model="data.form">
        <el-form-item label="課程名稱" >
          <el-input v-model="data.form.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="課程編號" >
          <el-input   v-model="data.form.courseNo" ></el-input>
        </el-form-item>

        <el-form-item label="課程描述" >
          <el-input   v-model="data.form.description" ></el-input>
        </el-form-item>

        <el-form-item label="課&nbsp;&nbsp;&nbsp;&nbsp;時" >
          <el-input   v-model="data.form.times" ></el-input>
        </el-form-item>

        <el-form-item label="任課老師" >
          <el-select v-model="data.form.teacher" placeholder="请选择任課老師">
            <el-option label="Jack" value="Jack"></el-option>
            <el-option label="Alan" value="Alan"></el-option>
            <el-option label="Tom" value="Tom"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveCourse">保 存</el-button>
        </span>
      </template>
    </el-dialog>





 </div>
</template>

<script setup>
import { reactive } from "vue"
import { Search } from '@element-plus/icons-vue'
import request from '@/utils/request'

const data = reactive({
  name: '',
  courseNo: '',
  tableData:[],
  total: 0,
  pageNum:1,
  pageSize:2,
  dialogFormVisible: false,
  form:{
    
  }

})

const loadList=() => {
  console.log("pageNum:"+data.pageNum)
  request.get('/course/courseList', {
    params:{
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      courseNo: data.courseNo
    }
  }).then(res => {
    console.log(res)
    data.tableData = res.data?.list || []
    data.total= res.data.total || 0
  })

}

//調用方法
loadList();

const handleCurrentChange =(pageNum)=>{
  //console.log(pageNum)
  //雖然handleCurrentChange會將頁碼回傳到方法，但是這裡通過v-model將pageNum和pageSize綁定到組件上，直接調用loadList方法就行
  loadList(); 
}

const searchList= ()=>{
  loadList();
}

const resetSearch=()=>{
  data.name='';
  data.courseNo='';
  loadList();
}

const addCourse=()=>{
  data.form={};
  data.dialogFormVisible=true;
}

const saveCourse=()=>{
  request.request({
    url: data.form.id ? '/course/updateCourse' : '/course/saveCourse',
    method: data.form.id ? 'PUT':'POST',
    data: data.form
  }).then(res => {
    if(res.code==='200'){
      loadList();
      data.dialogFormVisible=false;
      ElMessage.success("操作成功")
    }else{
      ElMessage.error(res.msg)
    }
  })
}

const courseEdit=(index,row)=>{
  console.log(index)
  console.log(row)
  data.dialogFormVisible=true
  data.form = JSON.parse(JSON.stringify(row));
}

const courseDelete=(index,row)=>{
  console.log(row.id)
  ElMessageBox.confirm(
    '確認刪除該信息嗎?',
    'Warning',
    {
      confirmButtonText: '確認',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(res => {
    request.delete('/course/deleteCourse/'+ row.id).then(res => {
        if(res.code==='200'){
          loadList();
          data.dialogFormVisible=false;
          ElMessage.success("操作成功")
        }else{
          ElMessage.error(res.msg)
        }
      })
    }) .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Delete canceled',
      })
    })
      




 

}


</script>





<style scoped>
.searchbox{
  margin-bottom: 10px;
}

</style>



