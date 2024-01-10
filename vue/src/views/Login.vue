<template>
    <div class="login-container">
        <div style="width: 350px;" class="login-box">
            <div style="font-weight:bold;font-size:24px;text-align:center;margin-bottom:30px">登錄</div>
            <el-form :model="data.form"   ref="formRef"  :rules="rules">
                <el-form-item prop="username">
                    <el-input prefix-icon="User" v-model="data.form.username" placeholder="請輸入賬號"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input show-password prefix-icon="Lock" v-model="data.form.password" placeholder="請輸入密碼"></el-input>
                </el-form-item>
                
                <el-form-item prop="role">
                    <div class="mb-2 flex items-center text-sm">
                        <el-radio-group v-model="data.form.role" class="ml-4">
                            <el-radio label="ADMIN" size="large" >管理員</el-radio>
                            <el-radio label="STUDENT" size="large" >學生</el-radio>
                        </el-radio-group>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" style="width: 100%;" @click="login">登 錄</el-button>
                </el-form-item>

            </el-form>
            <div style="margin-top:30px;text-align:right">
                還沒有賬號？請<a href="/register">註冊</a>
            </div>


        </div>


    </div>

</template>

<!-- vue3的語法-->
<script setup>
import {reactive,ref} from "vue"
import request from "@/utils/request"
import { ElMessage } from 'element-plus'
import router from "../router";



const data = reactive({
    form: {}

})


const rules = reactive({
    username: [
        { required: true, message: 'Please input username', trigger: 'blur' },
        { min: 3, max: 12, message: 'Length should be 3 to 12', trigger: 'blur' }
    ],
    password: [
        { required: true, message: 'Please input password', trigger: 'blur' },
        { min: 3, max: 12, message: 'Length should be 3 to 12', trigger: 'blur' }
    ],

})

const formRef = ref();

const login = () =>{
    formRef.value.validate((valid)=>{
        console.log(valid)
        if(valid){
            request.post('/login',data.form).then(res => {
                if(res.code === '200'){
                    localStorage.setItem('student-user',JSON.stringify(res.data));
                    ElMessage({
                        message: '登錄成功',
                        type: 'success',
                    })
                    router.push('/home')
                    //location.href='/home';
                }else{
                    ElMessage.error(res.msg)
                }
            })

        }
    })
}

</script>


<style scoped>
.login-container{
    min-height: 100vh;
    overflow: hidden;
    border:1px solid gray;
    display: flex;
    align-items: center;
    justify-content: center;
    background-image: url("@/assets/imgs/bg.png");
    background-size: cover;
}

.login-box{
    background-color: #fff;
    box-shadow:  0 0 10px rgba(0, 0, 0, 0,1);
    padding:30px;
    border-radius: 5px;


}

</style>