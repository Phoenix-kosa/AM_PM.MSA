<template>
    <body>
    <div class="logo-container">
        <img src="@/assets/images/main_logo.png" alt="">
    </div>
    <div class="row justify-content-center mt-5">
        <div class="col-md-6">
            <div id="id_card" class="card">
                <div class="card-header text-center">
                    <h2>로그인</h2>
                </div>
                <div class="card-body">
                    <form id="login_form" @submit.prevent="login">
                        <div class="form-group">
                            <label for="user_id">아이디</label>
                            <input v-model="request.userId" type="text" class="form-control" id="user_id" name="user_id"
                                placeholder="아이디를 입력해 주세요" required>
                        </div>
                        <div class="form-group">
                            <label for="password">비밀번호</label>
                            <input v-model="request.password" type="password" class="form-control" id="password" name="password"
                                placeholder="비밀번호를 입력해 주세요" required>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block" id="login-btn">로그인</button>
                    </form>
                    <button id="register_button" class="btn btn-primary btn-block" @click="$router.push('/register')">
                    <span id="register_text">회원가입</span></button>
                    <img class="" id="google-btn" v-on:click="simpleLogin" :src="googlebtn">
                </div>
            </div>
        </div>
    </div>
    </body>
</template>
<script setup>
import { reactive } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';

// axios.defaults.withCredentials = true;

import { useRouter } from 'vue-router';

import googlebtn from "@/assets/images/google_btn.png"

const router = useRouter();
const request = reactive({
    userId : '',
    password : ''
})

const login = () => {
    // console.log(request.userId  + "/"+ request.password)
    axios.post("http://ampm.com:8088/api/auth/local", request, {
            headers: { 
                "content-type" : "application/json" }
            })
        .then(response => {
            if (response.status == 200) {
                // 로그인 성공 시 메인 페이지로
                console.log(response.headers.authorization);
                console.log(response.headers.refreshtoken);

                sessionStorage.setItem("access-token", response.headers.authorization);
                sessionStorage.setItem("refresh-token", response.headers.refreshtoken);
                Swal.fire({
                    title: 'Success!',
                    text: '로그인 성공!',
                    icon: 'success',
                    confirmButtonText: 'OK',
                }).then(result => {
                    if(result.value){
                        router.push("/project-list")
                    }}
                )
            } else {
                console.log(response)
                // 로그인 실패 시 오류 메시지를 팝업 창으로 표시
                response.text().then(errorMessage => {
                    alert(errorMessage);
                });
            }
        })
        .catch(error => {
            Swal.fire({
                    title: '로그인 실패!',
                    text: '아이디 및 비밀번호를 확인해주세요',
                    icon: 'warning',
                    confirmButtonText: 'OK',
                })
            console.error(error);
            console.log(error.response.status)
        });
}

const simpleLogin = () => {
    window.location.href = "http://ampm.com:8088/oauth2/authorization/google";
}
</script>

<style scoped>
@import "@/assets/css/userLogin.css";
</style>