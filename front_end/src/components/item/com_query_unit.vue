<template>
    <div class="t2b pa-3">
        <!-- 프로필, 이름, 상품평 -->
        <div class="l2r">
            <v-avatar color="secondary" rounded="0" >
                <p :style="style">{{ tag }}</p>
            </v-avatar>
            <div class="t2b">
                <p>{{ $util.beAnonymous(user.name) }}</p>
                
                <h5 class="text-grey">{{ user.option }}</h5>
            </div>

            <v-spacer />

            <p>{{ $util.str2date(createdAt) }}</p>
        </div>
        
        <!-- 내용 -->
        <v-spacer class="my-3" />
        <p>{{ content }}</p>

        <v-spacer class="my-3" />

        <v-expansion-panels v-if="!isAnswer">
            <v-expansion-panel>
                <v-expansion-panel-title>
                    <template v-slot:default="{ expanded }">
                        <h5>{{ labelQuery(expanded) }}</h5>
                        <div class="hidden">{{ eventHandler(expanded) }}</div>
                    </template>
                </v-expansion-panel-title>
                <v-expansion-panel-text class="bg-grey-lighten-3">
                    <div v-for="unit of children_QnAs" :key="unit">
                        <com_query_unit v-bind="unit" :user="unit.user" isAnswer />
                        <v-divider />
                    </div>
                </v-expansion-panel-text>
            </v-expansion-panel>
        </v-expansion-panels>
    </div>
</template>

<script>
import { ErrRes, QnARes } from '@/dto';

export default {
props: {
    qid: String,
    user: Object,
    kind: String,
    createdAt: String,
    content: String,

    isAnswer: {
        type: Boolean,
        default: false,
    },
},
data() {return {
    expanded: false,

    children_QnAs: [],
}},
methods: {
    isState(kind) {
        return (kind == "Q");
    },
    labelQuery(flag) {
        return flag ? "QnA 내역 닫기" : "QnA 내역 열람";
    },
    eventHandler(flag) {
        if(flag) {
            this.fetchChildrenQnAs();
        }
    },
    fetchChildrenQnAs() {
        this.$http.get(`/question/${this.qid}/children`).then(res => {
            this.children_QnAs = QnARes.of(res).pages();
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
},computed: {
    style() {
        const color = this.isState(this.kind) ? "green" : "red";
        return `color: ${color};`;
    },
    tag() {
        const color = this.isState(this.kind) ? "질문" : "답변";
        return `[${color}]`;
    },
},
}
</script>

<style scoped>
    .hidden {
        visibility: hidden;
    }
</style>
