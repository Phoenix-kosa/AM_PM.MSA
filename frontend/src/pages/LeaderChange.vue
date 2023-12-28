<template>
  <h1 class="">Change Leader</h1>
  <div class="center">
  <div class="container">
    <div class="list">
      <ul>
        <form class ="member-list" v-for="item in memberList" :key="item.id">
          <div style="color:blue; font-weight:bold" v-if="item.roles=='representative_member'" class="representative">
            <label :for="item.id"> {{ item.nickName }}</label>
          </div>
          <div v-else>
            <input class="checkbox" type="radio" v-model="userform" :value="item.userId">
            <label :for="item.id"> {{ item.nickName }}</label>
          </div>
        </form>
      </ul>
    </div>
  </div>
  <div class="clickbutton">
    <button @click="leaderChange" class="btn btn-primary">Change</button>
  </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { expireToken } from "../api/config";
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2';
const router = useRouter();

const memberList = ref([]);
let userform = ref();

const projectId = sessionStorage.getItem("projectId");

const leaderChange = () => {
  console.log(userform.value)
  if(userform){
    axios.put("http://localhost:8088/api/representative_member/" + projectId, {"members":[userform.value]}, {
      headers: {
        "Authorization" : sessionStorage.getItem("access-token") 
      }
    }).then(response => {
      console.log(response.status)
      Swal.fire({
            title: 'Success!',
            text: '프로젝트 대표를 변경했습니다!',
            icon: 'success',
            confirmButtonText: 'OK',
        }).then(result => {
            if(result.value){
                router.push("/member-list")
            }}
        )
    })
    .catch((err) => {
      console.log(err)
      expireToken(err, leaderChange, userform)
    });
  } else {
    Swal.fire({
      title: 'Info!',
      text: '체크한 맴버가 없습니다',
      icon: 'info',
      confirmButtonText: 'OK',
    })
  }
}

function loadData(){
  axios.get(`http://localhost:8088/api/members/` + projectId, {
    headers: { 
        "Authorization" : sessionStorage.getItem("access-token") 
    }
  })
  .then((response) => {
    memberList.value = response.data.sort((a, b) => {
        // roles 값을 기준으로 정렬 (알파벳순으로 정렬)
        if (a.roles < b.roles) {
          return 1;
        }
        if (a.roles > b.roles) {
          return -1;
        }
        return 0;
      });
  })
  .catch((err) => {
    console.log(err);
    expireToken(err, loadData)
  });
}
loadData();
</script>
<style scoped>
@import "@/assets/css/memberlist.css";
</style>
