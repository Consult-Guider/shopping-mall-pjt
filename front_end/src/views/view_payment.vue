<template>
    <v-container>
        <v-row>
            <v-col><com_payment_header /></v-col>
        </v-row>
        <v-row>
            <v-col><com_payment_sender :value="data.sender" @input="x=>{data.sender=x;}"/></v-col>
        </v-row>
        <v-row>
            <v-col><com_payment_receiver :value="data.receiver" @input="x=>{data.receiver=x;}"/></v-col>
        </v-row>
        <v-row>
            <v-col><com_payment_item :value="data.item" @input="x=>{data.item=x;}" @select="updateSelected"/></v-col>
        </v-row>
        <v-row>
            <v-col><com_payment_payment :selected="selected"/></v-col>
        </v-row>
        <v-row>
            <v-col class="d-flex justify-center">
                <v-btn
                class="btn-payment"
                color="primary"
                >
                    결제하기
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {return {
        data: {
            sender: {
                name: "김철수",
                email: "asdf@gmail.com",
                phoneNum: "010-1234-5678",
            },
            receiver: {
                name: "김철수",
                address: "경기도 김포시 풍무동",
                phoneNum: "010-1234-5678",
                toDelivery: "조심히 와주세요."
            },
            item: [
                {iid: 1234, name: "GPU", option: "4090", num: 2, price: 10000},
                {iid: 1212344, name: "GPU", option: "3090", num: 1, price: 10000},
                {iid: 121234, name: "GPU", option: "2090", num: 4, price: 10000},
            ],
            payment: null,
        },

        selected: [],
    }},
    methods: {
        fetchInfos: async function() {
            console.log("call fetchInfos");
            // TODO: 첫 로드 시, 데이터 불러오기.
        },

        onClickButton() {
            console.log("call onClickButton");
            // TODO: 버튼 클릭 시, payment 데이터 전달하기.
        },
        updateSelected(x) {
            let list = [];
            for(const idx in x) {
                list.push(this.data.item[idx]);
            }
            this.selected = list;
        },
    },

    async created() {
        this.fetchInfos();
    },
}
</script>

<style scoped>
    .btn-payment {
        width: 200px;
    }
</style>