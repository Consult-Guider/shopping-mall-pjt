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
        <com_make_query v-bind="item" />
      </v-card>
    </v-dialog>
</template>

<script>
export default {
props: {
    item: Object,
},
data() {return {
    btnQuery: {
        label: "문의하기",
        click: this.onClickQuery,
        dialog: false,
    },

    comments: [
        {
            user: {name: "이선영", option: "데스크탑"},
            kind: "Q",
            createdAt: "20221204",
            content: "윈도우는 깔려서 오는 건가요?",
        },
        {
            user: {name: "카카오팀", option: " "},
            kind: "A",
            createdAt: "20221204",
            content: "이게 맞나?",
        },
    ],

    page: 1,
    total: 100,
}},
methods: {
    onClickQuery() {
        console.log("click onClickQuery");
        this.btnQuery.dialog = true;
    },
},
}
</script>

<style scoped>

</style>
