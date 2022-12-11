<template>
<v-container>
<v-row>
<v-col>
    <com_item_info_create :imgList="imgList" />
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
        <v-tab value="review" disabled>상품평</v-tab>
        <v-tab value="query" disabled>상품 문의</v-tab>
        <v-tab value="exchange" disabled>배송/교환/반품 안내</v-tab>
        </v-tabs>

        <v-card-text>                    
            <v-window v-model="tab">
                <v-window-item value="desc">
                    <com_desc_create @updateImg="(imgList=>this.imgList=imgList)" />
                </v-window-item>

                <v-window-item value="review">
                    <!-- <com_review /> -->
                </v-window-item>

                <v-window-item value="query">
                    <!-- <com_query /> -->
                </v-window-item>

                <v-window-item value="exchange">
                    <!-- <com_exchange /> -->
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
data() {return {
    tab: "desc",

    imgList: [],
}},
}
</script>

<style scoped>

</style>