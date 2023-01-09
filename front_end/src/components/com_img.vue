<template>
    <img 
        class="clickable"
        :src="Src" 
        @click="onClickAD"
    />
</template>

<script>
export default {
props: {
    src: String,
    link: String,
    no_link: {
        type: Boolean,
        default: false,
    },
},
computed: {
    Src() {return this.src ?? this.$defaults.image;},
    Link() {return this.link ?? this.$defaults.link;},
},
methods: {
    onClickAD() {
        console.log(`click onClickAD. ${this.Link}`);
        if(!this.no_link) {
            location.href = this.Link;
        }
    },
},
}
</script>

<style scoped>
    img {
        object-fit: contain;

        max-height: 400px;
    }
</style>