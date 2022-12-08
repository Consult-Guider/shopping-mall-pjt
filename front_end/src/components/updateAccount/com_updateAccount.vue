<template>
    <h2>회원정보 수정</h2>

    <v-divider thickness="3" color="black"/>

    <v-table theme="grey" density="comfortable">
    <thead>
    <tr>
        <th class="text-right">
            아이디(이메일)
        </th>
        <th class="text-right">
            이름
        </th>
        <th class="text-right">
            휴대폰 번호
        </th>
        <th class="text-right">
            비밀번호변경
        </th>
        <th class="text-right">
            배송지
        </th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <v-text-field
            v-model="model.email" 
            variant="plain" density="compact" hide-details active
            ></v-text-field>
        </td>
        <td>
            <v-text-field
            v-model="model.name" 
            variant="plain" density="compact" hide-details
            ></v-text-field>
        </td>
        <td>
            <v-text-field
            v-model="model.phoneNum" 
            variant="plain" density="compact" hide-details
            ></v-text-field>
        </td>
        <td>
            <v-text-field
            v-model="model.password" 
            variant="plain" density="compact" hide-details
            ></v-text-field>
        </td>
        <td>
            <v-text-field
            v-model="model.address" 
            variant="plain" density="compact" hide-details
            ></v-text-field>
        </td>
    </tr>
    <tr>
    </tr>
    </tbody>
    </v-table>

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
    }},
    watch: {
        model(val) {
            this.$emit("update", val);
        }
    },
    methods: {
        setError(message) {
            this.error = message;
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
            this.setError("");
        },
        onClickUpdate() {
            console.log("call onClickUpdate");
            // TODO: axios를 이용해서 업데이트 쿼리 날리기.
        },
    },
}
</script>

<style scoped>
    table tr {
        display:flex;
        flex-direction: column;
        float: left;
    }

    thead,tbody {
        float: left;
    }

    th {
        background-color: rgb(200, 200, 200);
        width: 200px;
    }
    td {
        width: 300px;
    }

    .rowGap > * {
        margin-left: 10px;
        margin-right: 10px;
        width: 150px;
    }
</style>