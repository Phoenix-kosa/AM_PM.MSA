<template>
  <h2>1:1 문의</h2>
  <div id="table" class="board-detail">   
    <div class="board-contents">
      <h3 style="margin-left: 4px;">제목: {{ Qtitle }}</h3><hr>
      <div>
        <strong class="w3-large" style="margin-left: 4px;">번호: {{ id }}</strong><br>
        <strong class="w3-large" style="margin-left: 4px;">작성자: {{ userId }}</strong>
        <strong style="margin-left: 20%;">작성일: {{ createdDate }}</strong>
      </div>
      <hr><br>
      <div class="board-contents">
        <span style="margin-left: 4px;">{{ Qcontent }}</span><br>         
      </div>
      <div class="d-grid gap-2 d-md-flex justify-content-md-end" style="margin-bottom: 5px; margin-right: 3px;">
        <button v-show="roles!='ROLE_ADMIN'" type="button" class="btn btn-outline-primary" v-on:click="fnUpdate">수정</button>
        <button type="button" class="btn btn-outline-primary" v-on:click="fnQdelete">삭제</button>
        <button type="button" class="btn btn-outline-primary" v-on:click="fnList">목록</button>
      </div>
    </div>
  </div>      
  <br>
  <div v-show="getTitle!=''" class="card-header" style="border: 2px solid darkkhaki;">
    <h2>답변</h2> 
    <hr>  
      <div>
        <h4 style="margin-left: 4px;">제목: {{ getTitle }}</h4>
      </div>
      <hr><br>
      <div class="board-contents">
        <span style="margin-left: 4px;">{{ getContent }}</span><br>
        <button v-show="roles=='ROLE_ADMIN'" type="button" class="btn btn-outline-primary" v-on:click="fnAdelete" style="margin-left: 89%; margin-bottom: 5px;">삭제</button>         
      </div>

  </div>
  <table v-show="roles=='ROLE_ADMIN'" style="margin-top: 20px; margin-left: 25%;">      
    <div class="mb-3">
      <input type="text" v-model="Atitle" class="form-control" placeholder="제목을 입력하세요.">
      <textarea name="" id="" cols="90" rows="10" v-model="Acontent" class="form-control form-control-sm" placeholder="내용을 입력하세요." style="resize: none;" required></textarea>
      
    </div>
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <button type="button" class="btn btn-outline-primary" v-on:click="fnASave">저장</button>
      
    </div>
  </table>  
</template>

<script>

import Swal from 'sweetalert2';
import axios from 'axios';
import { toHandlers } from 'vue';
import { expireToken } from "@/api/config";
export default{
  data(){
    return {
      requestBody: this.$route.query,
      id: this.$route.query.id,
      questionId: '',
      userId: '',
      Qtitle: '',      
      Qcontent: '',
      Atitle: '',
      Acontent: '',
      AcreatedDate: '',
      createdDate: '',     
      getTitle: '',
      getContent: '',
      roles: '',
    }
  },
  mounted(){
    this.fnGetQuestion()
    this.fnGetAnswer()
    this.fnGetUser()
    // this.fnGetView2()
  },
  methods: {
    fnGetQuestion: function(){
      axios.get("http://192.168.3.84:8088/api/question/"+this.id,{
        params: this.requestBody
      }).then((res) => {
        console.log(res)
        this.Qtitle = res.data.title
        this.userId = res.data.userId
        this.Qcontent = res.data.content
        this.createdDate = res.data.createdDate
      }).catch(err => {
        expireToken(err, this.fnGetQuestion);
      })
    },
    fnGetAnswer: function(){
      axios.get("http://192.168.3.84:8088/api/answer/"+this.id,{
      }).then((res) => {
        console.log(res)
        this.getTitle = res.data.title
        this.getContent = res.data.content
        this.AcreatedDate = res.data.createdDate
      }).catch(err => {
        expireToken(err, this.fnGetAnswer);
      })
    },

    fnGetUser: function(){
      axios.get("http://192.168.3.84:8088/api/user", {headers: { 
          "Authorization" : sessionStorage.getItem("access-token") }       
      }).then((res) => {
        console.log(res)
        this.roles = res.data.role;
        
      }).catch(err => {
        expireToken(err, this.fnGetUser);
      })
    },

    fnList() {
      delete this.requestBody.id
      this.$router.push({
        path: './question',
        query: this.requestBody
      })
    },

    fnAList() {
      delete this.requestBody.id
      this.$router.push({
        path: './answer',
        query: this.requestBody
      })
    },
    fnUpdate(){
      Swal.fire({
            icon: 'warning',
            title: 'Warning!',
            text: '정말 수정하시겠습니까?',
            confirmButtonText: 'Ok!',
            showCancelButton: true,
            cancelButtonText: 'Cancel!'
          }).then(result => {
            if(result.isConfirmed){
              this.$router.push({
              path: './write',
              query: this.requestBody
              })
            }
          })

    },
    fnQdelete: function(){
      Swal.fire({
            icon: 'warning',
            title: 'Warning!',
            text: '정말 삭제하시겠습니까?',
            confirmButtonText: 'Ok!',
            showCancelButton: true,
            cancelButtonText: 'Cancel!'
          }).then(result => {
            if(result.isConfirmed){
              axios.delete("http://192.168.3.84:8088/api/question/"+this.id,{})
                .then(() => {
                  Swal.fire({
                    icon: 'success',
                    title: 'SUCCESS!',
                    text: '삭제되었습니다.',
                    confirmButtonText: 'ok',
                  });
                  this.fnList();
                }).catch(err => {
                  expireToken(err, this.fnQdelete);
              })    
            }
      })
    },
    fnASave(){
      let apiUrl = "http://192.168.3.84:8088/api/answer/write"
      this.form = {
        "questionId": this.id,
        "title": this.Atitle,
        "content": this.Acontent
      }
      console.log(this.form)

      axios.post(apiUrl, this.form)
        .then(response => {
          Swal.fire({
            icon: 'success',
            title: 'SUCCESS!',
            text: '저장되었습니다.',
            confirmButtonText: 'Ok!',
          });
          console.log(response.data);
          this.fnGetQuestion();
          this.$router.push({
          path: './admin-question',
          query: this.requestBody
          })
        }).catch(err => {
          console.error(error);
          
        })              
    },
    fnAdelete(){
      Swal.fire({
            icon: 'warning',
            title: 'Warning!',
            text: '정말 삭제하시겠습니까?',
            confirmButtonText: 'Ok!',
            showCancelButton: true,
            cancelButtonText: 'Cancel!'
          }).then(result => {
            if(result.isConfirmed){
              axios.delete("http://192.168.3.84:8088/api/answer/"+this.id)
                .then(result => {
                  console.log(result)
                  Swal.fire({
                      icon: 'success',
                      title: 'SUCCESS!',
                      text: '삭제되었습니다.',
                      confirmButtonText: 'Ok!'
                    }).then(() =>{
                        this.$router.push({
                        path: "/admin-question",
                        query: this.requestBody
                      })
                    });
              })    
            }
          }).catch(err => {
            expireToken(err, this.fnAdelete);
      })
        
    },

  }  
}
</script>
<style scoped>
#table {
  border: 2px solid skyblue;
}
</style>