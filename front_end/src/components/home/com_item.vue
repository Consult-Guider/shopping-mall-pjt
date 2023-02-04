<template>
    <v-container v-if="data" class="t2b center">
        <com_img class="box x-center size-limit" v-bind="data" />
        <h5>{{ data.name }}</h5>
        <h5>{{ `${$util.transPrice(data.price)}원` }}</h5>
    </v-container>
</template>

<script>
export default {
    props: {
        data: Object,
    },
}
</script>

<style scoped>
.size-limit {
    max-height: 175px;
}
</style>