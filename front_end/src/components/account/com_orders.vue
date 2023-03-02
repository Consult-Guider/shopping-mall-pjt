<template>
    <v-card>
        <v-btn-toggle
            v-model="tab"
            color="deep-purple-accent-3" rounded="0"
            group
        >
            <v-btn 
                v-for="item in tabs" :key="item"
                :value="item.value"
            >
                {{ item.name }}
            </v-btn>
        </v-btn-toggle>

        <v-card-text class="border-line">
            <com_orders_unit 
                v-for="item of items" :key="item"
                v-bind="item" class="mb-6"
                @update="fetchItems"
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
import { ErrRes, PageReq, PurchaseRes } from '@/dto';

export default {
data() {return {
    page: 1,
    total: 1,

    tab: "READY",
    tabs: this.$env.com_orders,

    items: [],
}},
watch: {
    page() {
        this.fetchItems();
    },
    tab() {
        this.fetchItems();
    },
},
created() {
    this.fetchItems();
},
methods: {
    fetchItems() {
        const api = this.$env.util_com_orders(this.$env.com_orders, this.tab, "api");
        const page = PageReq.of(this.page-1, 5).sort("createdAt", false);

        this.$auth.get(api, {params: page.params()}).then(res => {
            const response = PurchaseRes.of(res);
            this.items = response.pages();
            this.total = response.getTotalPages();
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
.v-btn {
    width: 100px;
}
</style>
