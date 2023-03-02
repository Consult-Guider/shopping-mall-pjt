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
                        {{ thing.num }}개
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
                @click="btnDelSelected.click"
            >{{ btnDelSelected.label }}</v-btn>
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
import { BucketAllReq, ErrRes, PurchaseRes } from '@/dto';
const qs = require('qs');

export default {
data() {return {
    total: {
        delivery: 0,
    },

    checkboxSelectAll: {
        value: false,
        label: "전체 선택",
    },

    things: [],

    btnDelSelected: {
        label: "바구니에서 삭제",
        click: this.onClickDelAll,
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
created() {
    this.fetchPurchaseReady();
},
methods: {
    fetchPurchaseReady() {
        this.$auth.get(`/payment/READY`).then(res => {
            const page = PurchaseRes.of(res).pages();
            this.things = page.map(this.transform);
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
    transform(unit) {
        return {
            pid: unit.pid,
            name: unit.name, 
            price: unit.price, 
            num: unit.num, 
            selected: false, 
            img: unit.src, 
        };
    },
    onClickKeepShop() {
        this.$router.push(this.$endPoint.home);
    },
    onClickDelAll() {
        const data = BucketAllReq.of(this.pidsSelected).json();
        this.$auth.delete(`/payment/READY`, {
            params: data,
            paramsSerializer: params => {
                return qs.stringify(params, {arrayFormat: 'repeat'})
            }
        }).then(() => {
            this.fetchPurchaseReady();
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
    onClickPurchase() {
        const data = BucketAllReq.of(this.pidsSelected).json();
        this.$auth.post(`/payment/DONE`, data).then(() => {
            alert("성공적으로 구매하였습니다.");
            this.fetchPurchaseReady();
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
    pidsSelected() {
        return this.things.filter(x => x["selected"]).map(x => x["pid"]);
    },
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