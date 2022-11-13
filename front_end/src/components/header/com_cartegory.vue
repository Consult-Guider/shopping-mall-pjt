<template>
    <div class="cartegory box d-flex justify-center align-center">
        <div
            @click="btn.click"
            class="text-center text-white clickable"
        >
            <v-icon>{{btn.icon}}</v-icon>
            <h3>{{btn.label}}</h3>
        </div>
    </div>
</template>

<script>
export default {
data() {return {
    btn: {
        icon: "mdi-menu",
        label: "카테고리",
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

    background-color: cornflowerblue;
}
div > * {
    margin: auto;
    font-size: 0.75rem;
}
</style>