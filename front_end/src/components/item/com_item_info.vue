<template>
<v-container>
<v-row>
    <v-col>
        <com_img class="fill" no_link :src="item.img"/>
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
            </v-col></v-row>
        </v-container>
    </v-col>
</v-row>
</v-container>
</template>

<script>
import { ErrRes, BucketReq } from '@/dto';

export default {
props: {
    data: Object,
},
watch: {
    data(val) {
        this.item.iid = val.iid;
        this.item.img = val.imgPath;
        this.item.name = val.name;
        this.item.price = val.price;
        this.option.list = val.options.map(this.optionMapper);
    },
},
data() {return {
    item: {
        iid: null,
        img: null,
        name: "{상품명}", 
        price: "{상품 가격}",
    },
    option: {
        label: "옵션 선택",
        value: null,
        list: [],
    },
    incredment: {
        max: 100,
        value: 1,
    },
    bucket: {
        label: "장바구니 담기",
        click: this.onClickBucket,
    },
}},
methods: {
    onClickBucket() {
        if(!this.item.iid) {
            alert("상품이 지정되지 않았음.");
            return ;
        }
        const data = BucketReq.of(this.item.iid, this.incredment.value, this.option.value).json();

        this.$auth.post(`/payment/READY`, data).then(() => {
            alert("해당 상품이 장바구니로 이동되었습니다!");
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

    optionMapper(unit) {
        return unit.name;
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
