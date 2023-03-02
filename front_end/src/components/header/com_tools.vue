<template>
    <div
        class="d-flex align-center"
    >
        <v-btn 
            v-if="isUser"
            @click="btnHolder.click"
            stacked
        >
            <v-icon :icon="btnHolder.icon"/>
            <p class="text-sm">{{ btnHolder.label }}</p>
        </v-btn>
        <v-btn 
            v-if="isLogin"
            @click="btnMyPage.click"
            stacked
        >
            <v-icon :icon="btnMyPage.icon"/>
            <p class="text-sm">{{ btnMyPage.label }}</p>
        </v-btn>
        <v-btn 
            v-if="isLogin"
            @click="btnLogout.click"
            stacked
        >
            <v-icon :icon="btnLogout.icon"/>
            <p class="text-sm">{{ btnLogout.label }}</p>
        </v-btn>
        <v-btn 
            v-if="!isLogin"
            @click="btnLogin.click"
            stacked
        >
            <v-icon :icon="btnLogin.icon"/>
            <p class="text-sm">{{ btnLogin.label }}</p>
        </v-btn>
    </div>
</template>

<script>
export default {
    data(){return {
        btnMyPage: {
            icon: "mdi-file-find",
            label: "마이페이지",
            click: this.onClickMyPage,
        },
        btnHolder: {
            icon: "mdi-cart",
            label: "장바구니",
            click: this.onClickHolder,
        },
        btnLogin: {
            icon: "mdi-account",
            label: "로그인",
            click: this.onClickLogin,
        },
        btnLogout: {
            icon: "mdi-account",
            label: "로그아웃",
            click: this.onClickLogout,
        },
    }},
    computed: {
        isLogin() {
            return this.$store.getters.isLogin;
        },
        isUser() {
            return this.$store.getters.isUser;
        },
    },
    methods: {
        onClickMyPage() {
            console.log("click onClickMyPage");
            this.$router.push(this.$endPoint.myPage);
        },
        onClickHolder() {
            console.log("click onClickHolder");
            this.$router.push(this.$endPoint.bucket);
        },
        onClickLogin() {
            console.log("click onClickLogin");
            this.$router.push(this.$endPoint.login);
        },
        onClickLogout() {
            console.log("click onClickLogout");
            this.$store.commit('logout');
        },
    }
}
</script>

<style scoped>
.text-sm {
    font-size: 0.1rem;
}
</style>