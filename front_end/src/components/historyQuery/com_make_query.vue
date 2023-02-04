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
                    <com_query_chat :chats="chats" />
                    <com_input_comment @post="postingEventHandler" />
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { ErrRes, QnAReq } from '@/dto';

export default {
props: {
    iid: String,
    chats: Array,
    name: String,
},
computed: {
    qid() {
        const firstChat = this.chats[0];
        return firstChat.qid;
    },
},
methods: {
    postingEventHandler(text) {
        if(!text) {
            alert("내용을 작성해주세요!");
            return ;
        }
        
        if(!this.qid) {
            this.createQuery(text);
        } else {
            this.updateQuery(text);
        }
    },

    updateQuery(text) {
        const data = QnAReq.of(text).json();

        this.$auth.post(`/question/${this.qid}/children`, data).then(() => {
            this.$emit("update");
        }).catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                default:
                    alert(errorCode);
                    break;
            }
        });
    },
    createQuery(text) {
        const data = QnAReq.of(text).json();

        this.$auth.post(`item/${this.iid}/question`, data).then(() => {
            this.$emit("update");
        }).catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
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

</style>