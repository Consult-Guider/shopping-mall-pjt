<template>
    <v-card>
        <v-btn-toggle
            v-model="tab"
            color="deep-purple-accent-3" rounded="0"
            group
        >
            <v-btn value="total">
                전체
            </v-btn>

            <v-btn value="ready">
                배송 준비중
            </v-btn>

            <v-btn value="ing">
                배송 중
            </v-btn>

            <v-btn value="done">
                배송 완료
            </v-btn>
        </v-btn-toggle>

        <v-card-text class="border-line">
            <com_orders_unit 
                v-for="item of items" :key="item"
                v-bind="item" class="mb-6"
            />
        </v-card-text>

        <!-- 페이지네이션 바 -->
        <v-pagination
        total-visible="5"
        v-model="page"
        :length="total"
        ></v-pagination>
    </v-card>
</template>

<script>
export default {
data() {return {
    page: 1,
    total: 100,

    tab: "total",

    items: [
        {orderedAt: 20220205, state: "ing", name: "에이먼 먼지 제거제", price: 12300, num: 2, src: "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/1c8a/b38f3a8042198fd731c7f32d1355890b5f85e4214a7c9b1cbf2c068497ef.jpg"},
        {orderedAt: 20220205, state: "ready", name: "에이먼 먼지 제거제", price: 12300, num: 2, src: "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/1c8a/b38f3a8042198fd731c7f32d1355890b5f85e4214a7c9b1cbf2c068497ef.jpg"},
        {orderedAt: 20220205, state: "done", name: "에이먼 먼지 제거제", price: 12300, num: 2, src: "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/1c8a/b38f3a8042198fd731c7f32d1355890b5f85e4214a7c9b1cbf2c068497ef.jpg"},
    ],
}},
}
</script>

<style scoped>
.v-btn {
    width: 100px;
}
</style>
