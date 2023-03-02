<template>
<v-container>
<v-row class="noGap border-line" align="center">
    <v-col cols="auto">
        <category_name :name="name"/>
    </v-col>

    <v-divider vertical />
    
    <v-col>
        <category_items :items="items"/>
    </v-col>
</v-row>
</v-container>
</template>

<script>
export default {
    props: {
        items: {
            type: Array,
            default: new Array(),
        },
        name: {
            type: String,
            default: "빈 카테고리",
        },
    },
}
</script>

<style scoped>
.noGap > * {
    padding: 0px;
}
</style>