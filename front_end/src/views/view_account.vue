<template>
    <v-container>
        <v-row>
            <v-col>
                <com_account_header :statistic="state" />
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <com_orders />
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { ErrRes, PurchaseStatisticRes } from '@/dto';

export default {
data() {return {
    state: {},
}},
created() {
    this.fetchStatistic();
},
methods: {
    fetchStatistic() {
        this.$auth.get(`/payment/statistic`).then(res => {
            const response = PurchaseStatisticRes.of(res);
            this.state = response.json();
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