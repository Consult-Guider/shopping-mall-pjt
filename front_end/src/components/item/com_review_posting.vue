<template>
    <div>
    <v-divider class="my-2"/>

    <v-card variant="outlined">
        <v-card-title>상품평 달기</v-card-title>
        <v-rating v-model="rating" />
        <v-card-text>{{rating}}점</v-card-text>
        <com_input_comment :content="content" @post="postingEventHandler" />
    </v-card>

    <v-divider class="my-2"/>
    </div>
</template>

<script>
export default {
props: {
    rate: Number,
    content: String,
},
watch: {
    rate(val) {
        this.rating = val;
    },
},
created() {
    this.rating = this.rate;
},
data() {
    return {
        rating: 3,
    }
},
methods: {
    postingEventHandler(text) {
        this.$emit("update", {
            rating: this.rating,
            text: text,
        });
    },
},
}
</script>

<style scoped>

</style>