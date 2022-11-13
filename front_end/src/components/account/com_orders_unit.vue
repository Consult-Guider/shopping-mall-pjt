<template>
    <v-card>
        <v-card-text>
        <p class="text-h6 text--primary">
            {{ $util.str2date(orderedAt) }} 주문
        </p>
        <v-card>
            <!-- 사진, 이름, 가격, 갯수 -->
            <div class="t2b">
                <h2 class="mt-6 ml-6">{{ transState(state) }}</h2>
                <div class="l2r">
                    <com_img class="img-size-limit" no_link :src="src" />
                    <div class="t2b">
                        <p>{{ name }}</p>
                        <div class="l2r">
                            <p class="text-grey mr-6">{{ $util.transPrice(price) }} 원</p>
                            <p class="text-grey mr-6">{{ num }} 개</p>
                        </div>
                    </div>
                </div>
            </div>
        </v-card>
        <v-card>
            <!-- 사진, 이름, 가격, 갯수 -->
            <div class="l2r children-btn pa-3">
                <v-btn
                    color="primary"
                    @click="btnListUp.click"
                >{{ btnListUp.label }}</v-btn>

                <v-btn
                    color="primary"
                    @click="btnExchange.click"
                >{{ btnExchange.label }}</v-btn>

                <v-btn
                    color="primary"
                    @click="btnReview.click"
                >{{ btnReview.label }}</v-btn>

                <v-btn
                    color="primary"
                    @click="btnQuery.click"
                >{{ btnQuery.label }}</v-btn>
            </div>
        </v-card>
        </v-card-text>
    </v-card>
</template>

<script>
export default {
props: {
    orderedAt: Number, 
    state: String, 
    name: String, 
    price: Number, 
    num: Number,
    src: String,
},
data() {return {
    btnListUp: {
        label: "배송조회",
        click: this.onClickListUp,
    },
    btnExchange: {
        label: "교환, 반품 신청",
        click: this.onClickExchange,
    },
    btnReview: {
        label: "리뷰 작성하기",
        click: this.onClickReview,
    },
    btnQuery: {
        label: "판매자 문의",
        click: this.onClickQuery,
    },
}},
methods: {
    transState(state) {
        if(state=="ready") {
            return "배송 준비중";
        } else if(state=="ing") {
            return "배송 중";
        } else if(state=="done") {
            return "배송 완료";
        } else {
            return "상태 이상";
        }
    },
    onClickListUp() {
        console.log("click onClickListUp");
    },
    onClickExchange() {
        console.log("click onClickExchange");
    },
    onClickReview() {
        console.log("click onClickReview");
    },
    onClickQuery() {
        console.log("click onClickQuery");
    },
},
}
</script>

<style scoped>
.img-size-limit {
    width: 125px;
}
.children-btn > .v-btn {
    width: 100px;

    font-size: x-small;
}
</style>