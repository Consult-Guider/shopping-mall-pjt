<template>
    <v-container>
        <v-row>
            <v-col>
                <h1>리뷰 내역 조회</h1>
                <v-divider thickness="thick" color="black"/>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <!-- 상품평 -->
                <v-card height="500" class="overflow-y-auto">
                    <div v-for="review of reviews" :key="review">
                        <com_review_header_unit v-bind="review"/>
                        <com_review_unit v-bind="review" />
                        <v-divider class="my-3" />
                    </div>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
props: {
    reviews: Array,
},
}
</script>

<style scoped>

</style>