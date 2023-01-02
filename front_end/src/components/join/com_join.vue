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
        <v-window v-model="role">
            <v-window-item
                v-for="item in roles" :key="item" 
                :value="item"
            >
            <v-container>
                <v-row v-for="row of roleData[item]" :key="row">
                    <v-text-field
                        clearable variant="outlined" class="mb-3" persistent-hint 
                        :prepend-icon="row.icon" :type="row.type"
                        :label="row.label" :hint="row.hint"
                        v-model="row.value"
                    ></v-text-field>
                </v-row>
            </v-container>
            </v-window-item>
        </v-window>
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
import roleType from "@/utils/roleType";

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
        type: null,
    },
    password: {
        label: "비밀번호",
        value: undefined,
        icon: "mdi-lock",
        hint: "",
        type: "password",
    },
    passwordAgain: {
        label: "비밀번호 확인",
        value: undefined,
        icon: "mdi-lock",
        hint: "",
        warn: "비밀 번호가 동일하지 않습니다.",
        type: "password",
    },
    name: {
        label: "이름",
        value: undefined,
        icon: "mdi-badge-account-horizontal-outline",
        hint: "",
        type: null,
    },
    phone: {
        label: "전화번호",
        value: undefined,
        icon: "mdi-cellphone-text",
        hint: "",
        type: null,
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

    role: roleType.roles.USER,
    roleData: {},
}},
created() {
    this.roleData = {
        USER:   [this.email, this.password, this.passwordAgain, this.name, this.phone,],
        SELLER: [this.email, this.password, this.passwordAgain, this.name, this.phone,],
        ADMIN:  [this.email, this.password, this.passwordAgain, this.name, this.phone,],
    };
},
computed: {
    valAgreeAll() {return this.agreeAll.value;},
    valAgreeOptions() {
        for(const option of this.agreeOptions) {
            if(!option.value) { return false; }
        }
        return true;
    },
    roles() {
        return roleType.roles;
    },
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