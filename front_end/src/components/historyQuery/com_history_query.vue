<template>
    <v-container>
        <v-row>
            <v-col>
                <h1>문의 내역 조회</h1>
                <v-divider thickness="thick" color="black"/>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="5">
                <v-card 
                elevation="5" 
                >
                    <v-item-group class="outer-left-shell overflow-auto">
                        <v-item 
                        v-for="item in items" :key="item"
                        v-slot="{ isSelected, toggle }"
                        >
                            <v-card
                            :color="isSelected ? 'grey-lighten-2' : ''" @click="toggle"
                            class="d-flex align-center pa-3" 
                            >
                                <com_query_state :item=item :isSelected=isSelected @changeChat="onChangeChat" />
                            </v-card>
                        </v-item>
                    </v-item-group>

                    <v-pagination
                    v-model="page"
                    :length="total"
                    ></v-pagination>
                </v-card>
            </v-col>
            <v-col>
                <v-card 
                elevation="5" 
                class="overflow-auto"
                >
                    <com_query_chat class="outer-right-card" :chats="chats" />
                    <com_input_comment @post="postingEventHandler" />
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { ErrRes, QnARes, QnAReq } from '@/dto';

export default {
    props: {
        items: Array,

        totalPages: Number,
    },
    data() {return {
        dataSelected: null,

        chats: [],

        page: 1,
        total: 1,
    }},
    watch: {
        page(val) {
            this.$emit("updatePage", val);
        },
    },
    methods: {
        onChangeChat(data) {
            console.log(data.qid)
            if(data) {
                this.dataSelected = data;
                this.fetchQuestion();
            }
        },

        fetchQuestion() {
            if(!this.dataSelected.qid) {
                return ;
            }

            this.$http.get(`/question/${this.dataSelected.qid}/children`).then(res => {
                const arrayChats = [this.dataSelected];
                
                const pages= QnARes.of(res).pages();
                arrayChats.push(...pages);

                this.chats = arrayChats;
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

        postingEventHandler(text) {            
            const data = QnAReq.of(text).json();

            this.$auth.post(`/question/${this.dataSelected.qid}/children`, data).then(() => {
                this.fetchQuestion();
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
    .outer-left-shell {
        height: 555px;
    }
    .outer-right-card {
        min-height: 500px;
    }
</style>