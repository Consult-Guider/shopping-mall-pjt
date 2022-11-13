<template>
    <v-container class="t2b center">
        <com_img class="box x-center size-limit" v-bind="data" />
        <h5>{{ data.name }}</h5>
        <h5>{{ `${$util.transPrice(data.price)}원` }}</h5>
    </v-container>
</template>

<script>
export default {
    props: {
        data_retained: Number,
    },
    data() {return {
        data: {
            src: "https://thumbnail6.coupangcdn.com/thumbnails/remote/160x160ex/image/vendor_inventory/2330/addbde5c0bf9d3c4962ed113d0d2f831aa7ed419b13dfbd77fbdd805c63f.jpg",
            link: null,
            name: "존재하지 않는 아이템",
            price: 1234253,
        }
    }},
}
</script>

<style scoped>
.size-limit {
    max-height: 175px;
}
</style>