<template>
<v-container>
<v-row>
<v-col>
    <com_item_info />
</v-col>
</v-row>

<v-row>
<v-col>
    <com_recommend />
</v-col>
</v-row>

<v-row>
<v-col>
    <v-card>
        <v-tabs
            v-model="tab"
            bg-color="grey"
            fixed-tabs
        >
        <v-tab value="desc">상품 상세</v-tab>
        <v-tab value="review">상품평</v-tab>
        <v-tab value="query">상품 문의</v-tab>
        <v-tab value="exchange">배송/교환/반품 안내</v-tab>
        </v-tabs>

        <v-card-text>                    
            <v-window v-model="tab">
                <v-window-item value="desc">
                    <com_desc :item="item" />
                </v-window-item>

                <v-window-item value="review">
                    <com_review :item="item" />
                </v-window-item>

                <v-window-item value="query">
                    <com_query :item="item" />
                </v-window-item>

                <v-window-item value="exchange">
                    <com_exchange />
                </v-window-item>
            </v-window>
        </v-card-text>
    </v-card>
</v-col>
</v-row>
</v-container>
</template>

<script>
export default {
computed: {
    iid() {return this.$route.params.iid;},
},
data() {return {
    tab: "desc",

    item: {
        imgPath: "mock imgPath",
        name: "mock name",
        price: "mock price",
        options: [],

        descriptions: [],
        reviews: [],
        queries: [],
    },
}},
}
</script>

<style scoped>

</style>