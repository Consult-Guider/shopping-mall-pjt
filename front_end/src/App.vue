<template>
  <v-app>
    <v-layout>
      <v-app-bar>
        <com_header class="header" @clickCartegory="toggleMenu" />
      </v-app-bar>

      <v-navigation-drawer 
        permanent class="bg-nav"
        v-model="drawer"
      >
        <com_account_nav />
      </v-navigation-drawer>
  
      <v-main>
        <router-view />
      </v-main>
    </v-layout>

    <v-footer class="footer">
      <com_footer />
    </v-footer>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data: () => ({
    drawer: false,
  }),
  methods: {
    toggleMenu() {
      this.drawer = !this.drawer;
    },
  },
}
</script>

<style>
.footer {
  background-color: black;
  max-height: 15vh;
}
.header {
  max-height: 15vh;
}
.bg-nav {
  background-color: rgb(200, 200, 200);
}

/* 전역 CSS */
/* 마우스 아이콘 변경 */
.clickable {
  cursor: pointer;
}
/* 반응형 정사각형 유지 */
.box {
  position: relative;
  width: 100%;
}
.box::after {
  display: block;
  content: "";
  padding-bottom: 100%;
}
/* 위아래로 나열 */
.t2b {
  display: flex;
  flex-direction: column;
}
/* 좌우로 나열 */
.l2r {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.l2r > * {
  margin-right: 5px;
}
/* 위아래 같은 비율로 간격 벌리기 */
.y-gap {
    display: flex;
    flex-direction: column;
    justify-content: center;
}
/* 좌우 가운데 정렬 */
.x-center {
    margin: auto;
}
.child-x-center > .v-row {
  display: flex;
  justify-content: center;
}
/* 위아래 가운데 정렬 */
.y-center {
    display: flex;
    align-items: center;
}
/* 꽉 채우기 */
.fill {
    height: 100%;
    width: 100%;
}
/* 테두리 선 그리기 */
.border-line {
  border: 1px solid black;
}
.text-a-center {
  text-align: center;
  position: relative;
  top: 50%;
  transform: translateY(-50%);
}

/* 디버깅용 */
.v-container {
  /* margin-right: 2px; */
  /* background-color: rgb(30, 255, 0); */
}
.v-row {
  /* margin-right: 2px; */
  /* background-color: rgb(255, 0, 0); */
}
.v-col {
  /* margin-right: 2px; */
  /* background-color: blue; */
}
</style>
