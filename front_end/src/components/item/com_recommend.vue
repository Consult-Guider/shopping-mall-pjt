<template>
    <div class="t2b">
        <h4>다른 고객이 함께 구매한 상품</h4>

        <v-spacer class="my-3" />

        <category_items id="items-rec" rows="1" cols="6" />
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.category_items {
    height: 100px;
}
</style>
