<template>
    <v-container>
        <v-row>
            <v-col>
                <!-- 상품평 -->
                <v-card height="500" class="overflow-y-auto">
                    <div v-for="review of reviews" :key="review">
                        <div class="l2r">
                            <com_review_unit v-bind="review" />
                            <v-spacer/>
                            <v-btn
                            @click="linkTo(review.iid)"
                            >해당 상품 페이지로</v-btn>
                        </div>
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
methods: {
    linkTo(iid) {
        this.$router.push(this.$endPoint.item(iid));
    },
},
}
</script>

<style scoped>

</style>