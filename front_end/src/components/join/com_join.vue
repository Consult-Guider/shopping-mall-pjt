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
            <v-text-field
                clearable variant="outlined"
                :prepend-icon="passwordAgain.icon" persistent-hint  type="password"
                v-model="passwordAgain.value"
                :label="passwordAgain.label"
                :hint="passwordAgain.hint"
            ></v-text-field>
        </v-row>
        <v-row>
            <v-text-field
                clearable variant="outlined"
                :prepend-icon="name.icon" persistent-hint
                v-model="name.value"
                :label="name.label"
                :hint="name.hint"
            ></v-text-field>
        </v-row>
        <v-row>
            <v-text-field
                clearable variant="outlined"
                :prepend-icon="phone.icon" persistent-hint
                v-model="phone.value"
                :label="phone.label"
                :hint="phone.hint"
            ></v-text-field>
        </v-row>
        <v-row>
            <v-checkbox
                v-model="agreeAll.value"
                :label="agreeAll.label"
                color="primary"
                :messages="agreeAll.hint"
            ></v-checkbox>
            <p>{{ agreeAll.desc }}</p>
        </v-row>
        <v-row 
            v-for="option of agreeOptions" :key="option"
            class="mb-n5"
        >
            <v-checkbox
                v-model="option.value"
                :label="transOptionLabel(option.required, option.label)"
                color="primary" hide-details
            ></v-checkbox>
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
    cfg: {
        option:{
            filled: "[필수]", none: "[선택]",
        },
        warn: {
            agreeOptions: "필수 약관에 동의를 해주십시오.",

        },
    },

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
    passwordAgain: {
        label: "비밀번호 확인",
        value: undefined,
        icon: "mdi-lock",
        hint: "",
        warn: "비밀 번호가 동일하지 않습니다.",
    },
    name: {
        label: "이름",
        value: undefined,
        icon: "mdi-badge-account-horizontal-outline",
        hint: "",
    },
    phone: {
        label: "전화번호",
        value: undefined,
        icon: "mdi-cellphone-text",
        hint: "",
    },

    agreeAll: {
        label: "모두 확인하였으며 동의합니다.",
        value: undefined,
        hint: "",
        desc: "\
        전체 동의에는 필수 및 선택 정보에 대한 동의가 포함되어 있으며, 개별적으로 동의를 선택 하실 수 있습니다. \
        선택 항목에 대한 동의를 거부하시는 경우에도 서비스 이용이 가능합니다. \
        "
    },
    agreeOptions: [
        {required: true, label: "만 14세 이상입니다.", value: false,},
        {required: true, label: "쿠팡 이용약관 동의", value: false,},
        {required: false, label: "전자금융거래 이용약관 동의", value: false,},
    ],

    btnJoin: {
        label: "동의하고 가입하기",
        click: this.onClickJoin,
    },

}},
computed: {
    valAgreeAll() {return this.agreeAll.value;},
    valAgreeOptions() {
        for(const option of this.agreeOptions) {
            if(!option.value) { return false; }
        }
        return true;
    }
},
watch: {
    valAgreeAll(val) {
        if(val){
            for(const option of this.agreeOptions) { option.value = true; }
        }
    },
    valAgreeOptions(val) {
        if(!val){
            this.agreeAll.value = false;
        }
    },
},
methods: {
    transOptionLabel(required, label) {
        const prefix = required ? this.cfg.option.filled : this.cfg.option.none;

        return `${prefix}  ${label}`;
    },
    fetchJoin(email, password, name, phone) {
        email, password, name, phone
    },
    clearHint() {
        const nullValue = "";

        this.email.hint = nullValue;
        this.password.hint = nullValue;
        this.passwordAgain.hint = nullValue;
        this.agreeAll.hint = nullValue;
    },
    verifyPasswordAgain() {
        if(this.password.value != this.passwordAgain.value) {
            this.passwordAgain.hint = this.passwordAgain.warn;
            return false;
        } else {
            return true;
        }
    },
    verifyOptions() {
        for(const option of this.agreeOptions) {
            if(option.required && !option.value) {
                this.agreeAll.hint = this.cfg.warn.agreeOptions;
                return false;
            }
            return true;
        }
    },

    onClickJoin() {
        console.log("click onClickJoin");
        this.clearHint();
        if(!this.verifyPasswordAgain()) {
            return ;
        }
        if(!this.verifyOptions()) {
            return ;
        }
        this.fetchJoin(this.email.value, this.password.value, this.name.value, this.phone.value);
        this.$router.push(this.$endPoint.home);
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