<template>
    <v-container>
        <v-row>
            <v-col>
                <com_updateAccount :value="data" />
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { LoginRes, ErrRes } from "@/dto";

export default {
    data() {return {
        data: {
            uid: null,
            email: null,
            name: null,
            phoneNum: null,
            address: null,
        },
    }},
    methods: {
        fetchProfile: async function() {
            const endPoint = this.$endPoint.backend[this.role];

            // data 업데이트.
            this.$auth.get(`${endPoint}/principal`)
            .then(res => {
                const response = LoginRes.of(res);

                const new_data = {
                    uid: response.id,
                    email: response.email,
                    name: response.name,
                    phoneNum: response.phoneNum,
                    address: response.address,
                };

                this.data = new_data;
            })
            .catch(err => {
                const errorCode = ErrRes.of(err).errorCode;
                
                // 에러 메세지 표시.
                switch(errorCode) {
                    default:
                        alert(errorCode);
                        break;
                }
            })
        },
    },
    computed: {
        role() {
            return this.$store.state.user.role;
        }, 
        isLogin() {
            return this.$store.getters.isLogin;
        },
    },
    created() {
        if(this.isLogin) {
            this.fetchProfile();
        }
    },
}
</script>

<style scoped>

</style>