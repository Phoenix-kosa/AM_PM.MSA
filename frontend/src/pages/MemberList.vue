<template>
  <h2 class="" style="text-align: center;">멤버 목록</h2>
  <div class="center">
  <div class="container">
    <div class="list">
      <ul>
        <div class ="member-list" v-for="item in memberList" :key="item.id">
          <div v-if="item.roles=='representative_member'" class="representative">
            <label :for="item.id"> {{ item.nickName }}</label>
          </div>
          <div v-else>
            <input class="checkbox" type="checkbox" v-model="userform" :value="item.userId" :disabled="!leadercheck">
            <label :for="item.id"> {{ item.nickName }}</label>
          </div>
        </div>
      </ul>
    </div>
  </div>
  <div class="clickbutton" v-if="leadercheck">
    <button @click="addMember" class="btn btn-primary">멤버 추가</button>
    <button @click="removeMember" class="btn btn-primary">멤버 제거</button>
    <button @click="leaderChange" class="btn btn-primary">프로젝트 대표 변경</button>
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
let userform = ref([]);
let leadercheck = ref(false);
const projectId = sessionStorage.getItem("projectId");

function addMember() {
  router.push("/add-member");
}

function leaderChange() {
  router.push("/leader-change");
}

const removeMember = () => {
  let formdata = []
  for(let o of userform.value){
    formdata.push(o);
  }

  console.log(formdata)
  if(formdata.length> 0){
    axios.delete("http://192.168.3.84:8088/api/members/" + projectId, {
      headers: {
        "Authorization" : sessionStorage.getItem("access-token") 
      } , data: {"members":formdata}
    }).then(response => {
      console.log(response.status)
      Swal.fire({
            title: 'Success!',
            text: '맴버를 제거했습니다!',
            icon: 'success',
            confirmButtonText: 'OK',
        }).then(result => {
            if(result.value){
              window.location.reload();
            }}
        )
    })
    .catch((err) => {
      console.log(err)
      expireToken(err, removeMember, formdata)
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
  axios.get(`http://192.168.3.84:8088/api/members/` + projectId, {
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

const getrepResentativeMember = () => {
  axios.get(`http://192.168.3.84:8088/api/representative_member/` + projectId, {
    headers: { 
        "Authorization" : sessionStorage.getItem("access-token") 
    }
  })
  .then(response => {
    leadercheck.value = response.data
    console.log(leadercheck.value)
  }).catch(err => {
    console.log(err)
    expireToken(err, getrepResentativeMember)
  })
}
loadData();
getrepResentativeMember();
</script>

<style scoped>
@import "@/assets/css/memberlist.css";
</style>
