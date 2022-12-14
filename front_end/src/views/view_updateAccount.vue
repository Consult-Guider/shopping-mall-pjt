<template>
    <v-container>
        <v-row>
            <v-col>
                <com_updateAccount :value="data" @update="x=>{tray=x;}"/>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {return {
        data: {
            uid: 1234,
            email: "asdf@gmail.com",
            name: "김철수",
            phoneNum: "010-1234-5678",
            address: "경기도 김소시 웅무동",
        },
        tray: {
            uid: null,
            email: null,
            name: null,
            phoneNum: null,
            address: null,
        },
    }},
    methods: {
        fetchProfile: async function() {
            console.log("call fetchProfile");
        },
    },
    created() {
        this.fetchProfile();
    },
}
</script>

<style scoped>

</style>