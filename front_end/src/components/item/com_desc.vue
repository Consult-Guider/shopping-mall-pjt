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
props: {
    item: Object,
},
watch: {
    item(val) {
        this.images = val.descriptions.map(this.transform);
    },
},
data() {return {
    images: [],
}},
methods: {
    transform(unit) {
        return unit.path;
    },
},
}
</script>

<style scoped>
.size-limit {
    max-height: 400px;
}
</style>
