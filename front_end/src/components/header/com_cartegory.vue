<template>
    <div class="cartegory box d-flex justify-center align-center">
        <div
            @click="btn.click"
            class="text-center text-white clickable"
        >
            <v-icon>{{btn.icon}}</v-icon>
        </div>
    </div>
</template>

<script>
export default {
data() {return {
    btn: {
        icon: "mdi-menu",
        click: this.onClickBtn,
    },
}},
methods: {
    onClickBtn() {
        console.log("onClickBtn");
        this.$emit("clickCartegory")
    },
},  
}
</script>

<style scoped>
.cartegory {
    padding: 5px;
    height: 60px;
    width: 60px;

    background-color: cornflowerblue;
}
div > * {
    margin: auto;
    font-size: 2.0rem;
}
</style>