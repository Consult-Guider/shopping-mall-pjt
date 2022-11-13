<template>
    <v-card class="t2b size-limit border-line pa-6">
        <v-card-title>
            <strong>취소/반품/교환/환불 내역</strong>
        </v-card-title>

        <v-card-subtitle>
            <ul>
                <li>
                    취소/반품/교환 신청한 내역을 확인할 수 있습니다.
                </li>
                <li>
                    하단 상품목록에 없는 상품은 1:1문의 또는 고객센터로 문의주세요.
                </li>
            </ul>
        </v-card-subtitle>
            
        <v-card-text>
            <!-- 취소 반품 내역 -->
            <com_exchanges_unit 
                v-for="item of items" :key="item"
                v-bind="item" class="mb-3"
            />

            <!-- 페이지네이션 바 -->
            <v-pagination
            total-visible="5"
            v-model="page"
            :length="total"
            ></v-pagination>
        </v-card-text>
    </v-card>
</template>

<script>
export default {
props: {
    items: Array,
},
data() {return {
    page: 1,
    total: 100,
}},
}
</script>

<style scoped>
.size-limit {
    min-height: 400px;
    width: 500px;
}
li {
    font-size: smaller;
}
</style>