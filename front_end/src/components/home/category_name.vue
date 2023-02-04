<template>  
    <v-container class="y-gap">
        <v-row class="t2b">
            <h1>태그명:</h1>
            <br>
            <h2>{{ data.category.name ?? $defaults.category }}</h2>
        </v-row>
    </v-container>
</template>

<script>
export default {
props: {
    name: {
        type: String,
        default: null,
    },
},
data() {return {
    data: {
        category: {
            name: this.name,
        }
    },
}},
watch: {
    name(val) {
        this.data.category.name = val;
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