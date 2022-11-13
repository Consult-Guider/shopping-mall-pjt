<template>
    <img 
        class="clickable"
        src="https://blog.kakaocdn.net/dn/6ui8u/btqDtzRrCzg/ZM8q87L5frjEl0za6UnIk1/img.jpg" 
        @click="onClickLogo"
    />
</template>

<script>
export default {
    methods: {
        onClickLogo: function() {
            this.$router.push(this.$endPoint.home);
        },
    }
}
</script>

<style scoped>
    img {
        max-width: 200px;
        max-height: 80%;
        object-fit: contain;
    }
</style>
