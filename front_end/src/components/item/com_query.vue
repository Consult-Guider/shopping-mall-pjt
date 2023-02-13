<template>
    <div class="t2b">
        <!-- 상품 문의 & 버튼 -->
        <div class="l2r">
            <h2>상품 문의</h2>
            <v-spacer />
            <v-btn
                color="primary" variant="outlined"
                @click="btnQuery.click"
            >{{ btnQuery.label }}</v-btn>
        </div>

        <v-spacer class="my-3" />

        <!-- 각종 설명 -->
        <ul>
            <li>구매한 상품의 취소/반품은 마이쿠팡 구매내역에서 신청 가능합니다. </li>
            <li>상품문의 및 후기게시판을 통해 취소나 환불, 반품 등은 처리되지 않습니다. </li>
            <li>가격, 판매자, 교환/환불 및 배송 등 해당 상품 자체와 관련 없는 문의는 고객센터 내 1:1 문의하기를 이용해주세요. </li>
            <li>"해당 상품 자체"와 관계없는 글, 양도, 광고성, 욕설, 비방, 도배 등의 글은 예고 없이 이동, 노출제한, 삭제 등의 조치가 취해질 수 있습니다. </li>
            <li>공개 게시판이므로 전화번호, 메일 주소 등 고객님의 소중한 개인정보는 절대 남기지 말아주세요. </li>
        </ul>

        <v-divider class="my-3" />

        <!-- 질의 답변 -->
        <div v-for="unit of comments" :key="unit">
            <com_query_unit v-bind="unit" :user="unit.user" />
            <v-divider />
        </div>

        <!-- 페이지네이션 -->
        <v-pagination
        v-model="page"
        :length="total"
        ></v-pagination>
    </div>

    <v-dialog v-model="btnQuery.dialog">
      <v-card>
        <com_make_query v-bind="item" :chats="chats" @update="fetchQueryChat" />
      </v-card>
    </v-dialog>
</template>

<script>
import { ErrRes, QnARes, PageReq, QnAUnitRes } from '@/dto';

export default {
props: {
    item: Object,
},
watch: {
    item(val) {
        this.comments = val.queries;
    },
    page() {
        this.fetchQuestionParents();
    }
},
created() {
    this.fetchQuestionParents();
    this.fetchQueryChat();
},
data() {return {
    btnQuery: {
        label: "문의하기",
        click: this.onClickQuery,
        dialog: false,
    },

    comments: [],

    page: 1,
    total: 1,

    chats: [],
}},
computed: {
    iid() {
        return this.item.iid;
    },
},
methods: {
    onClickQuery() {
        this.btnQuery.dialog = true;
    },

    fetchQuestionParents: async function() {
        const page = PageReq.of(this.page-1, 5).sort('createdAt', false);

        this.$http.get(`item/${this.iid}/question`, {params: page.params()}).then(res => {
            const response = QnARes.of(res);
            this.comments = response.pages();
            this.total = response.getTotalPages();
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

    fetchQueryChat: async function() {
        if(!this.$store.getters.isLogin) {
            alert("로그인 먼저 해주세요!!");
            return ;
        }

        this.$auth.get(`user/principal/item/${this.iid}/question`).then(res => {
            const firstChat = QnAUnitRes.of(res).json();
            this.chats = [firstChat];
            this.fetchQueryChatChildren(firstChat.qid);
        }).catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                case "QUESTION_NOT_FOUNDED":
                    break;
                default:
                    alert(errorCode);
                    break;
            }
        });
    },

    fetchQueryChatChildren: async function(qid) {
        this.$auth.get(`/question/${qid}/children`).then(res => {
            const children = QnARes.of(res).pages()
            this.chats.push(...children);
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
