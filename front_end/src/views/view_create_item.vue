<template>
<v-container>
<v-row>
<v-col>
    <com_item_info_create :imgList="imgList" :tagList="tagList" />
</v-col>
</v-row>

<v-row>
    <v-col>
        <v-divider class="my-2" />
        <div class="px-4">
            <h1>태그 추가</h1>
            <p>- 해당 태그는 검색결과에 반영될 수 있습니다.</p>
            <p>- 지나친 태그 남용은 운영진에 의해 제제당할 수 있다는 점 양해바랍니다.</p>
        </div>
        <com_tag_create @update="updateTagList" />
        <v-divider class="my-2" />
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
                    <com_desc_create @updateImg="updateImgList" />
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
    tagList: [],
}},
methods: {
    updateImgList(imgList) {
        this.imgList=imgList;
    },
    updateTagList(tagList) {
        this.tagList=tagList;
    },
},
}
</script>

<style scoped>

</style>