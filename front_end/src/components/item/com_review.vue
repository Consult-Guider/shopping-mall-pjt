<template>
    <div class="t2b">
        <!-- 별점 -->
        <h3>상품평</h3>
        <v-spacer class="my-2" />
        <p>동일한 상품에 대해 작성된 상품평으로, 판매자는 다를 수 있습니다.</p>
        <v-spacer class="my-3" />

        <div class="l2r">
            <v-rating
            v-model="rate"
            readonly
            ></v-rating>

            <h1 class="mx-5">{{ `평균 ${rate} 점 | 총 ${numRate} 개` }}</h1>

            <v-spacer />

        </div>

        <v-spacer class="my-3" />

        <!-- 상품평 검색 -->
        <v-card 
            variant="plain" class="bg-grey"
        >
            <v-card-text class="l2r">
                <v-btn-toggle
                v-model="sort"
                color="primary" variant="plain" divided
                >
                <v-btn value="rec-desc">공감순</v-btn>
                <v-btn value="rate-desc">평점순</v-btn>
                </v-btn-toggle>

                <v-text-field
                    variant="outlined" density="compact" 
                    append-inner-icon="mdi-magnify" bg-color="white"
                    :label="search.label" class="mx-5"
                    v-model="search.value"
                    @click:append-inner="search.click"
                    hide-details
                ></v-text-field>

                <v-spacer />
            </v-card-text>
        </v-card>

        <v-spacer class="my-3" />
        
        <!-- 상품평 -->
        <div v-for="review of reviews" :key="review">
            <com_review_unit v-bind="review" />
            <v-divider class="my-3" />
        </div>
        
        <!-- 페이지네이션 -->
        <v-pagination
        v-model="page"
        :length="total"
        ></v-pagination>

    </div>
</template>

<script>
export default {
data() {return {
    sort: "rec-desc",

    rate: 3.5,
    numRate: 17,
    reviews: [
        {
            user: {name: "이선한", option: "데스크탑"},
            rate: 4.5,
            createdAt: "20220102",
            content: "무난히 쓸만했습니당~",
            numRec: 24,
            numNotRec: 5,
        },
        {
            user: {name: "저스틴 비버", option: "바지"},
            rate: 2.5,
            createdAt: "20221202",
            content: "별루임.",
            numRec: 2,
            numNotRec: 8,
        },
    ],

    search: {
        value: "",
        label: "상품평 검색",
        click: this.onClickSearch,
    },

    page: 1,
    total: 100,
}},
methods: {
    onClickSearch() {
        console.log("click onClickSearch");
    },
},
}
</script>

<style scoped>

</style>
