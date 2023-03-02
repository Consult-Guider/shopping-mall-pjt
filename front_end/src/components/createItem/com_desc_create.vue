<template>
    <div class="t2b">
        <com_img_create 
            v-for="img of images" :key="img"
            @updateImg="updateImg" @deleteImg="deleteImg"
            class="fill my-2 size-limit" 
            :multipartfile="img"
        /> 
        <com_img_create disabled always_empty
            @updateImg="createImg"
            class="fill my-2 size-limit" 
        />
    </div>
</template>

<script>
export default {
data() {return {
    images: [],
}},
methods: {
    createImg(old_one, img) {
        this.images.push(img);

        this.$emit('updateImgList', this.images);
    },
    updateImg(old_one, img) {
        const idx = this.images.indexOf(old_one);
        if (idx > -1) this.images.splice(idx, 1, img);

        this.$emit('updateImgList', this.images);
    },
    deleteImg(img) {
        const idx = this.images.indexOf(img);
        if (idx > -1) this.images.splice(idx, 1);

        this.$emit('updateImgList', this.images);
    },
},
}
</script>

<style scoped>
.size-limit {
    max-height: 400px;
}
</style>
