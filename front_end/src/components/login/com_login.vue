<template>
    <v-container class="child-size-limit child-x-center">
        <v-row>
            <img_logo />
        </v-row>
        <v-row>
            <v-tabs
            v-model="role"
            bg-color="basil" class="mb-3"
            density="compact" centered stacked grow
            >

            <v-tab :value="roles.USER">
                <v-icon>mdi-account</v-icon>
                소비자
            </v-tab>
            <v-tab :value="roles.SELLER">
                <v-icon>mdi-domain</v-icon>
                판매자
            </v-tab>
            <v-tab :value="roles.ADMIN">
                <v-icon>mdi-police-badge</v-icon>
                운영자
            </v-tab>

            </v-tabs>
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
import { authReq, authRes, ErrRes } from "@/dto";
import roleType from "@/utils/roleType";

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

    role: roleType.roles.USER,
}},
computed: {
    roles() {
        return roleType.roles;
    },
},
methods: {
    fetchLogin(email, password, isAuto) {
        isAuto
        const data = authReq.of(this.role, email, password);

        this.clearHint();
        this.$http.post('/auth', data.json())
        .then(res => {
            // accessToken parsing.
            const token = authRes.of(res.data).token;

            // store에 적재.
            this.$store.commit('login', token);

            // 전 페이지로 이동.
            window.history.back();
        })
        .catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                case "ACCOUNT_NOT_FOUNDED":
                    this.email.hint = "해당 계정은 존재하지 않습니다.";
                    break;
                case "WRONG_PASSWORD":
                    this.password.hint = "잘못된 비밀번호입니다.";
                    break;
                default:
                    alert(errorCode);
                    break;
            }
        });
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