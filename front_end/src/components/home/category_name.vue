<template>  
    <v-container class="y-gap">
        <v-row class="t2b">
            <h1>{{ data.category.name ?? $defaults.category }}</h1>
            <br>
            <h6
                class="clickable"
                @click="onClickLink(data.category.link)"
            >바로가기 ></h6>
        </v-row>
        <v-row class="t2b">
            <h3>HOT 키워드</h3>
        </v-row>
    </v-container>
</template>

<script>
export default {
data() {return {
    data: {
        category: {
            name: null,
            link: null,

        }
    },
}},
methods: {
    onClickLink(link) {
        this.$router.push(link ?? this.$defaults.link);
    },
},
}
</script>

<style scoped>
.v-container {
    height: 500px;
    width: 200px;

    margin: 3px;
}
</style>