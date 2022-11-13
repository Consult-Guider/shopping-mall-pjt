<template>
    <div class="t2b">
        <com_img 
            v-for="img of images" :key="img"
            class="fill my-2 size-limit" 
            :src="img" no_link
        />
    </div>
</template>

<script>
export default {
data() {return {
    images: [
        null,
        null,
        null,
    ],
}},
}
</script>

<style scoped>
.size-limit {
    max-height: 400px;
}
</style>
