<template>
    <v-card>
        <v-card-text>
        <p class="text-h6 text--primary">
            {{ $util.str2date(orderedAt) }} 주문
        </p>
        <v-card>
            <!-- 사진, 이름, 가격, 갯수 -->
            <div class="t2b mx-6 my-3">
                <h2 class="mt-6">{{ transState(state) }}</h2>
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
                    v-for="item in btns" :key="item"
                    color="primary"
                    @click="item.click"
                    :disabled="item.banState.includes(state)"
                >{{ item.label }}</v-btn>
            </div>
        </v-card>
        </v-card-text>
    </v-card>
</template>

<script>
import { ErrRes, CancelReq } from '@/dto';

export default {
props: {
    pid: String,
    iid: String,
    orderedAt: Number, 
    state: String, 
    name: String, 
    price: Number, 
    num: Number,
    src: String,
},
data() {return {
    btns: {
        btnLink: {
            label: "해당 상품으로",
            click: this.onClickLink,
            banState: [],
        },
        btnExchange: {
            label: "취소 신청",
            click: this.onClickCancellation,
            banState: ["Cancellation"],
        },
    },
}},
methods: {
    transState(state) {
        let origin = this.$env.com_orders;
        return this.$env.util_com_orders(origin, state, "name");
    },
    onClickCancellation() {
        const data = CancelReq.of([this.pid]).json();

        this.$auth.post(`/payment/CANCEL`, data).then(() => {
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
    onClickLink() {
        this.$router.push(this.$endPoint.item(this.iid));
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