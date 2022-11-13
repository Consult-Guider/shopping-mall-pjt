<template>
    <v-container class="child-size-limit child-x-center">
        <v-row>
            <img_logo />
        </v-row>
        <v-row>
            <v-text-field
                clearable variant="outlined"
                :prepend-icon="email.icon" persistent-hint
                v-model="email.value"
                :label="email.label"
                :hint="email.hint"
            ></v-text-field>
        </v-row>
        <v-row>
            <v-text-field
                clearable variant="outlined"
                :prepend-icon="password.icon" persistent-hint type="password"
                v-model="password.value"
                :label="password.label"
                :hint="password.hint"
            ></v-text-field>
        </v-row>
        <v-row>
            <v-checkbox
                v-model="autoLogin.value"
                :label="autoLogin.label"
                color="primary"
                hide-details
            ></v-checkbox>
        </v-row>
        <v-row>
            <v-btn
                block color="primary"
                @click="btnLogin.click"
            >{{ btnLogin.label }}</v-btn>
        </v-row>
        <v-divider class="my-6" />
        <v-row>
            <v-btn
                block color="primary"
                @click="btnJoin.click"
            >{{ btnJoin.label }}</v-btn>
        </v-row>
    </v-container>
</template>

<script>
export default {
data() {return {
    email: {
        label: "아이디(이메일)",
        value: undefined,
        icon: "mdi-account",
        hint: "",
    },
    password: {
        label: "비밀번호",
        value: undefined,
        icon: "mdi-lock",
        hint: "",
    },
    autoLogin: {
        label: "자동 로그인",
        value: undefined,
    },

    btnLogin: {
        label: "로그인",
        click: this.onClickLogin,
    },
    btnJoin: {
        label: "회원가입",
        click: this.onClickJoin,
    },

}},
methods: {
    fetchLogin(email, password, isAuto) {
        email, password, isAuto
    },
    clearHint() {
        const nullValue = "";

        this.email.hint = nullValue;
        this.password.hint = nullValue;
    },

    onClickLogin() {
        console.log("click onClickLogin");
        this.fetchLogin(this.email.value, this.password.value, this.autoLogin.value);
    },
    onClickJoin() {
        console.log("click onClickJoin");
        this.$router.push(this.$endPoint.join);
    },
},
}
</script>

<style scoped>
.child-size-limit > * {
    width: 400px;

    margin: auto;
}
</style>