<template>
  <div class="">
    <h1>1:1 문의</h1><br>
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
      <button v-show="roles!='ROLE_ADMIN'" type="button" class="btn btn-outline-primary text-white bg-blue" v-on:click="fnWrite">문의등록</button>
    </div><br>
    <table class="table">
      <thead class="table-primary">
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>글쓴이</th>
          <th>상태</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row,idx) in list" :key="idx">
          <td>{{ row.id }}</td>
          <td><a v-on:click="fnView(`${row.id}`)" style="cursor:pointer">{{ row.title}}</a></td>
          <td>{{ row.userId }}</td>
          <td v-if="row.status == false" class="text-primary" style="">답변대기</td>
          <td v-else="row.status == true" class="text-success" >답변완료</td>
          <td>{{ row.createdDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

import Swal from 'sweetalert2';
import { expireToken } from "@/api/config";
import axios from 'axios';
import { reactive } from 'vue';
export default {
  data() {
    return {
      requestBody: {},
      list: reactive({}),
      no: '',
      status: false,
      roles: ''
    }
  },
  mounted() {
    this.fnGetList()
    this.fnGetUser()
  },
  methods: {
    fnGetList: function() {
      this.requestBody = {
        keyword: this.keyword,
        
      }
      axios.get("http://ampm.com:8088/api/question", {
        params: this.requestBody,
        headers: {"Authorization" : sessionStorage.getItem("access-token")}
      }).then((res) => {
        this.list = res.data
        // console.log(list)
        // console.log(list.value)
      }).catch(err => {
        console.log(err)
        expireToken(err, this.fnGetList);
      })     

    },

    fnGetUser: function(){
      axios.get("http://ampm.com:8088/api/user", {headers: { 
          "Authorization" : sessionStorage.getItem("access-token") }       
      }).then((res) => {
        console.log(res)
        this.roles = res.data.role;
        
      }).catch(err => {
        expireToken(err, this.fnGetUser);
      })
    },
    fnView(id){
      let p = './detail';
      if(this.roles =='ROLE_ADMIN'){
        p = "/admin-detail"
      }
      this.requestBody.id = id
      this.$router.push({
        path: p,
        query: this.requestBody
      })   
    },
    fnWrite() {
      this.$router.push({
        path: './write'
      })
    }
  }
  
}

</script>

<style scoped>
/* @import '@/assets/css/question.css'; */

.table {
  text-align: center;
}
</style>