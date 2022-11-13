<template>
<v-container>
<v-row>
    <v-col>
        <com_img class="fill" />
    </v-col>
    <v-col>
        <v-container id="left-sector">
            <v-row><v-col>
                <h3>{{ item.name }}</h3>
            </v-col></v-row>
            <v-divider />
            
            <v-row><v-col>
                <h3 class="text-red">{{ `${$util.transPrice(item.price)}원` }}</h3>
            </v-col></v-row>
            <v-divider />

            <v-row><v-col>
                <v-combobox
                v-model="option.value"
                :items="option.list"
                :label="option.label"
                multiple
                variant="outlined"
                ></v-combobox>
            </v-col></v-row>
            <v-divider />

            <v-row><v-col class="gap">
                <input type="number" id="theNumber" min="1" :max="incredment.max" v-model="incredment.value" />
                <v-btn
                    variant="outlined" color="primary"
                    @click="bucket.click"
                >{{bucket.label}}</v-btn>
                <v-btn
                    variant="flat" color="primary"
                    @click="purchase.click"
                >{{purchase.label}}</v-btn>
            </v-col></v-row>
        </v-container>
    </v-col>
</v-row>
</v-container>
</template>

<script>
export default {
data() {return {
    item: {
        name: "한성컴퓨터 프리워커 데스크탑 F2300W (i3-10100)", 
        price: 10000,
    },
    option: {
        label: "옵션 선택",
        value: null,
        list: ["양말", "지우개", "마우스"],
    },
    incredment: {
        max: 100,
        value: 1,
    },
    bucket: {
        label: "장바구니 담기",
        click: this.onClickBucket,
    },
    purchase: {
        label: "바로 구매",
        click: this.onClickPurchase,
    },
}},
methods: {
    onClickBucket() {
        console.log("click onClickBucket");
    },
    onClickPurchase() {
        console.log("click onClickPurchase");
    },
},
}
</script>

<style scoped>
#left-sector > .v-divider {
    margin-top: 20px;
    margin-bottom: 20px;
}
#theNumber {
    text-align: center;

    border: 1px solid grey;
    border-radius: 3px;
}
.gap > * {
    margin: 2px;
    height: 35px;
}
</style>
