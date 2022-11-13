<template>
    <v-card id="shell" class="t2b pa-6 y-gap" variant="outlined">
        <!-- 테이블 -->
        <v-table height="300">
            <thead>
                <tr>
                    <th>
                        <v-checkbox
                            class="mx-1" hide-details density="compact"
                            :label="checkboxSelectAll.label" v-model="checkboxSelectAll.value"
                        ></v-checkbox>
                        
                    </th>
                    <th>갯수</th>
                    <th>품절 여부</th>
                    <th>상품 금액</th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="thing of things" :key="thing">
                    <td>
                        <v-checkbox
                            class="mx-1" hide-details density="compact"
                            v-model="thing.selected"
                        >
                            <template v-slot:label>
                                <com_img 
                                    :src="thing.img" 
                                    no_link class="fill"
                                />
                                <p class="mx-6">{{ thing.name }}</p>
                            </template>
                        </v-checkbox>
                    </td>
                    <td>
                        <input type="number" min="1" v-model="thing.num"/>
                    </td>
                    <td>
                        <p>{{ strSoldOut(thing.soldOut) }}</p>
                    </td>
                    <td>{{ $util.transPrice(thing.num * thing.price) }}원</td>
                </tr>
            </tbody>
        </v-table>

        <!-- 전체선택 체크박스 -->
        <div class="l2r">
            <v-checkbox
                class="mx-1" hide-details density="compact"
                :label="checkboxSelectAll.label" v-model="checkboxSelectAll.value"
            ></v-checkbox>

            <v-btn
                @click="btnDelAll.click"
            >{{ btnDelAll.label }}</v-btn>

            <v-btn
                @click="btnDelSaleOut.click"
            >{{ btnDelSaleOut.label }}</v-btn>
        </div>

        <!-- 총합 계산기 -->
        <v-card
            class="pa-6"
            variant="outlined"
        >
            <h4 class="text-center">
                총 상품 가격 {{ $util.transPrice(totalStuff) }}원 <v-icon>mdi-plus</v-icon> 
                총 배송비 {{ $util.transPrice(total.delivery) }}원 <v-icon>mdi-equal</v-icon> 
                총 주문 금액 <strong class="text-red">{{ $util.transPrice(totalStuff + total.delivery) }}원</strong>
            </h4>
        </v-card>

        <!-- 버튼들 -->
        <div class="d-flex justify-center manage-buttons">
            <v-btn
                variant="outlined" color="primary"
                @click="btnKeepShop.click"
            >{{ btnKeepShop.label }}</v-btn>

            <v-btn
                variant="flat" color="primary"
                @click="btnPurchase.click"
            >{{ btnPurchase.label }}</v-btn>
        </div>
    </v-card>
</template>

<script>
export default {
data() {return {
    total: {
        delivery: 0,
    },

    checkboxSelectAll: {
        value: false,
        label: "전체 선택",
    },

    things: [
        {name: "삼다수", price: 10000, num: 3, selected: false, soldOut: false, img: "https://thumbnail10.coupangcdn.com/thumbnails/remote/120x120ex/image/retail/images/1149549744484312-e1226fce-9a3a-4c6c-9732-9e2cc9ff6f50.jpg", },
        {name: "GPU 4080", price: 100000, num: 1, selected: false, soldOut: true, img: "https://thumbnail10.coupangcdn.com/thumbnails/remote/120x120ex/image/retail/images/1149549744484312-e1226fce-9a3a-4c6c-9732-9e2cc9ff6f50.jpg", },
        {name: "제네시스", price: 5000000, num: 2, selected: false, soldOut: false, img: "https://thumbnail10.coupangcdn.com/thumbnails/remote/120x120ex/image/retail/images/1149549744484312-e1226fce-9a3a-4c6c-9732-9e2cc9ff6f50.jpg", },
    ],

    btnDelAll: {
        label: "전체 삭제",
        click: this.onClickDelAll,
    },
    btnDelSaleOut: {
        label: "품절 상품 삭제",
        click: this.onClickDelSaleOut,
    },
    btnKeepShop: {
        label: "계속 쇼핑하기",
        click: this.onClickKeepShop,
    },
    btnPurchase: {
        label: "바로 구매",
        click: this.onClickPurchase,
    },
}},
methods: {
    onClickDelAll() {
        console.log("click onClickDelAll");
        this.deleteAllInBucket();
    },
    onClickDelSaleOut() {
        console.log("click onClickDelSaleOut");
        this.deleteSoldOutInBucket();
    },
    onClickKeepShop() {
        console.log("click onClickKeepShop");
        this.$router.push(this.$endPoint.home);
    },
    onClickPurchase() {
        console.log("click onClickPurchase");
    },

    deleteAllInBucket() {
        this.things = [];
    },

    deleteSoldOutInBucket() {
        for(const thing of this.things) {
            if(thing.soldOut) {
                const idx = this.things.indexOf(thing);
                this.things.splice(idx, 1);
            }
        }
    },

    strSoldOut(val) {
        return val ? "품절" : null;
    }
},
watch: {
    selectAll(val) {
        if(val) {
            for(const thing of this.things) {
                thing.selected = true;
            }
        }
    },
    selectThings(val) {
        if(!val) {
            this.checkboxSelectAll.value = false;
        }
    },

},
computed: {
    selectAll() {
        return this.checkboxSelectAll.value;
    },
    selectThings() {
        for(const thing of this.things) {
            if(!thing.selected) {
                return false;
            }
        }
        return true;
    },
    totalStuff() {
        let result = 0;
        for(const thing of this.things) {
            if(thing.selected) {
                result += thing.price * thing.num;
            }
        }
        return result;
    },
},
}
</script>

<style scoped>
#shell {
    /* height: 500px; */
    width: 700px;
}
#shell > * {
    margin-top: 30px;
}
.manage-buttons > * {
    margin: 10px;
    width: 125px;
}
input {
    width: 50px;

    border: 1px solid black;
}
</style>