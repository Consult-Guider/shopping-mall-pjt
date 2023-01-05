<template>
    <h2>회원정보 수정</h2>

    <v-divider thickness="3" color="black"/>

    <v-container>
        <v-row>
            <v-col class="th">
                아이디(이메일)
            </v-col>
            <v-col class="td">
                <v-text-field
                v-model="model.email" 
                variant="outlined" density="compact" hide-details active
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col class="th">
                이름
            </v-col>
            <v-col class="td">
                <v-text-field
                v-model="model.name" 
                variant="outlined" density="compact" hide-details
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col class="th">
                휴대폰 번호
            </v-col>
            <v-col class="td">
                <v-text-field
                v-model="model.phoneNum" 
                variant="outlined" density="compact" hide-details
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row>
            <v-col class="th">
                비밀번호변경
            </v-col>
            <v-col class="td">
                <v-row>
                    <v-col class="th-inner">바꿀 비밀 번호</v-col>
                    <v-col>
                        <v-text-field
                        v-model="model.password" type="password"
                        variant="outlined" density="compact" hide-details
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col class="th-inner">비밀 번호 확인</v-col>
                    <v-col>
                        <v-text-field
                        v-model="password.again" type="password"
                        variant="outlined" density="compact" hide-details
                        ></v-text-field>
                    </v-col>
                </v-row>
                
            </v-col>
        </v-row>
        <v-row>
            <v-col class="th">
                배송지
            </v-col>
            <v-col class="td">
                <v-text-field
                v-model="model.address" 
                variant="outlined" density="compact" hide-details
                ></v-text-field>
            </v-col>
        </v-row>
    </v-container>

    <h3 class="text-red text-center pt-10">{{ error }}</h3>

    <v-spacer class="my-16"/>

    <div class="d-flex justify-end rowGap">
        <v-btn color="primary" variant="flat" @click="onClickReset">
            되돌리기
        </v-btn>
        <v-btn color="primary" variant="flat" @click="onClickUpdate">
            수정하기
        </v-btn>
    </div>
</template>

<script>
import { UserUpdateReq, ErrRes } from "@/dto";

export default {
    props: {
        value: Object,
    },
    data() {return {
        model: {
            uid: this.value.uid,
            email: this.value.email,
            name: this.value.name,
            phoneNum: this.value.phoneNum,
            password: null,
            address: this.value.address,
        },

        error: "",

        password: {
            again: null,
        },
    }},
    watch: {
        value() {
            this.onClickReset();
        },
    },
    methods: {
        setError(message) {
            this.error = message;
        },
        verifyPasswordEqual() {
            return this.model.password == this.password.again;
        },

        onClickReset() {
            console.log("call onClickReset");
            this.model = {
                uid: this.value.uid,
                email: this.value.email,
                name: this.value.name,
                phoneNum: this.value.phoneNum,
                password: null,
                address: this.value.address,
            };
            this.password = {
                again: null,
            },
            this.setError("");
        },
        onClickUpdate() {
            console.log("call onClickUpdate");

            // 비밀 번호 에러 사항 확인.
            if(!this.verifyPasswordEqual()) {
                this.error = "비밀번호가 같지 않습니다.";
                return ;
            } else {
                this.error = "";
            }
            // TODO: axios를 이용해서 업데이트 쿼리 날리기.
            const role = this.$store.state.user.role;
            const data = UserUpdateReq.of(role, this.model);
            const endpoint = this.$endPoint.backend[role];

            this.$auth.put(`${endpoint}/principal`, data.json())
            .then(() => {
                this.setError("수정 완료")
            })
            .catch(err => {
                const errorCode = ErrRes.of(err).errorCode;

                // 에러 메세지 표시.
                switch(errorCode) {
                    case "ACCOUNT_ALREADY_EXISTED":
                        this.email.hint = "해당 이메일이 이미 존재합니다.";
                        break;
                    case "INVALID_PARAMETER":
                        this.setError("적절한 형식이 아닙니다.")
                        break;
                    default:
                        alert(errorCode);
                        break;
                }
            });
        },
    },
}
</script>

<style scoped>
    .th {
        background-color: rgb(200, 200, 200);

        flex-grow: 0.3;

        font-weight: bold;
        font-size: 0.8em;

        padding: 4% 20px;
        text-align: right;

        border-top-style: solid;
        border-top-color: grey;
        border-top-width: 0.1em;

        border-right-style: solid;
        border-right-color: grey;
        border-right-width: 0.1em;
    }
    .td {
        align-content: center;

        margin-bottom: 10px;

        border-top-style: solid;
        border-top-color: grey;
        border-top-width: 0.1em;
    }

    .th-inner {
        flex-grow: 0.3;

        font-weight: bold;
        font-size: 0.8em;

        padding: 4% 20px;
        text-align: right;
    }

    .rowGap > * {
        margin-left: 10px;
        margin-right: 10px;
        width: 150px;
    }
</style>