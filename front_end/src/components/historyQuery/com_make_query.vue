<template>
    <v-container>
        <v-row>
            <v-col>
                <h2>'{{name}}'에 대한 문의 내용</h2>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <v-card 
                elevation="5" 
                class="outer-shell overflow-auto"
                >
                    <com_query_chat :qid="qid" :update="update_component.com_query_chat" />
                    <com_input_comment @post="postingEventHandler" />
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
props: {
    iid: String,

    name: String,
},
data() {return {
    qid: null,

    update_component: {
        com_query_chat: 0,
    },
}},
methods: {
    postingEventHandler(text) {
        console.log("call postingEventHandler: " + text);
        // 만약 qid가 NULL이면 새로 qid를 만든 후에 넣어주기.
        if(!this.qid) this.createQueryDomain();

        if(text) this.update_component.com_query_chat++;
        // TODO: text 데이터값을 받은 후 이를 문의 내역에 반영.
    },

    createQueryDomain() {
        console.log("call createQueryDomain: " + this.iid);
        // TODO: 아직 문의 도메인의 qid가 존재하지 않으므로 새로 생성 후, qid를 넣어줌.
        // axios를 이용해서 쿼리 날리기.
        this.qid = 1;
    },
},
}
</script>

<style scoped>

</style>