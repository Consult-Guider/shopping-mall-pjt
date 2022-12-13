<template>
    <v-divider class="my-2"/>

    <v-card variant="outlined">
        <v-card-title>상품평 달기</v-card-title>
        <v-rating v-model="rating" />
        <v-card-text>{{rating}}점</v-card-text>
        <com_input_comment @post="postingEventHandler" />
    </v-card>

    <v-divider class="my-2"/>
</template>

<script>
export default {
data() {
    return {
        rating: 3,
    }
},
methods: {
    postingEventHandler(text) {
        console.log("call postingEventHandler: " + text);

        // TODO: text 데이터값을 받은 후 이를 리뷰 내역에 반영.[axios 이용해서 새로 생성.]
        if(text) this.$emit("update");
    },
},
}
</script>

<style scoped>

</style>