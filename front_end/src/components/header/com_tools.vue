<template>
    <div
        class="d-flex align-center"
    >
        <v-btn 
            v-if="isLogin"
            @click="btnMyPage.click"
            stacked
        >
            <v-icon :icon="btnMyPage.icon"/>
            <p class="text-sm">{{ btnMyPage.label }}</p>
        </v-btn>
        <v-btn 
            v-if="!isLogin"
            @click="btnLogin.click"
            stacked
        >
            <v-icon :icon="btnLogin.icon"/>
            <p class="text-sm">{{ btnLogin.label }}</p>
        </v-btn>
        <v-btn 
            @click="btnHolder.click"
            stacked
        >
            <v-icon :icon="btnHolder.icon"/>
            <p class="text-sm">{{ btnHolder.label }}</p>
        </v-btn>
    </div>
</template>

<script>
export default {
    data(){return {
        debug: true,
        // TODO: 로그인 여부를 체크하는 부분을 반영하기.


        btnMyPage: {
            icon: "mdi-account",
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
    }},
    computed: {
        isLogin() {
            return this.debug;
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
    }
}
</script>

<style scoped>
.text-sm {
    font-size: 0.1rem;
}
</style>