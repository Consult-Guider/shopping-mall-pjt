<template>
    <div>
        <v-card class="pa-3">
            <v-card-title class="t2b">
                <h3 class="clickable" @click="onClickName">{{ name }}, <strong class="text-red">{{transState(state)}}</strong></h3>
                <h6 class="text-grey">{{option}}</h6>
            </v-card-title>

            <v-card-subtitle>
                {{price}}원, {{num}}개
            </v-card-subtitle>

            <v-card-text>
                <v-table class="border-line">
                    <thead>
                        <tr>
                            <th>반품 접수일</th>
                            <th>주문일</th>
                            <th>주문 번호</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{{$util.str2date(exchangeAt)}}</td>
                            <td>{{$util.str2date(orderedAt)}}</td>
                            <td>{{oid}}</td>
                        </tr>
                    </tbody>
                </v-table>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
export default {
props: {
    state: String,
    exchangeAt: String,
    orderedAt: String,
    iid: Number,
    oid: Number,
    name: String,
    option: String,
    num: Number,
    price: Number,
},
methods: {
    onClickName() {
        console.log("click onClickName");
        this.$router.push(this.$endPoint.item(this.iid));
    },
    transState(state) {
        if(state=="ready") {
            return "반품 준비중";
        } else if(state=="ing") {
            return "반품 중";
        } else if(state=="done") {
            return "반품 완료";
        } else {
            return "상태 이상";
        }
    },
},
}
</script>

<style scoped>

</style>