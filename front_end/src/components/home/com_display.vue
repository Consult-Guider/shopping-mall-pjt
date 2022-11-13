<template>
<v-container>
<v-row class="noGap border-line" align="center">
    <v-col cols="auto">
        <category_name />
    </v-col>

    <v-divider vertical />
    
    <v-col>
        <category_items />
    </v-col>
</v-row>
</v-container>
</template>

<script>
export default {

}
</script>

<style scoped>
.noGap > * {
    padding: 0px;
}
</style>