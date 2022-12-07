<template>
    <v-container v-if="(chats.length)">
        <v-row v-for="chat in chats" :key="chat">
            <v-col>
                <com_query_chat_unit v-bind="chat" />
            </v-col>
        </v-row>
    </v-container>
    <v-container v-else>
        <v-row>
            <v-col>
                <h1 class="text-center">선택된 문의 내역이 없습니다.</h1>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        qid: Number,
    },
    data() {return {
        chats: [
            // {
            //     state: "Qusetion",
            //     content: "문의 관련 내용 쏼라 쏼라",
            //     createdAt: 20220105,
            // },
            // {
            //     state: "Answer",
            //     content: "문의 관련 내용 답변 쏼라 쏼라",
            //     createdAt: 20220106,
            // },
            // {
            //     state: "Qusetion",
            //     content: "문의 관련 내용 쏼라 쏼라",
            //     createdAt: 20220105,
            // },
            // {
            //     state: "Answer",
            //     content: "문의 관련 내용 답변 쏼라 쏼라",
            //     createdAt: 20220106,
            // },
            // {
            //     state: "Qusetion",
            //     content: "문의 관련 내용 쏼라 쏼라",
            //     createdAt: 20220105,
            // },
            // {
            //     state: "Answer",
            //     content: "문의 관련 내용 답변 쏼라 쏼라",
            //     createdAt: 20220106,
            // },
        ],
    }},
    methods: {
        fetchQueryChat: function(qid) {
            console.log("call fetchQueryChat: " + qid);
            // TODO: 여기에 qid를 이용해서 axios로 백엔드에 챗팅 내역을 요구함. 나중에 구현하기.
        },
    },
    watch: {
        qid(val) {
            if(val) {
                this.fetchQueryChat(val);
            }
        },
    },
}
</script>

<style scoped>

</style>