<template>
<v-container>
<v-row align="center">
    <v-col cols="auto">
        <com_cartegory @clickCartegory="convey('clickCartegory')"/>
    </v-col>
    <v-col id="header--middle" cols="8" class="d-flex flex-row align-center">
        <img_logo id="logo" />
        <search_window id="search_window"/>
    </v-col>
    <v-col cols="auto">
        <com_tools />
    </v-col>
</v-row>
</v-container>
</template>

<script>
export default {
    data() {
        return {
            
        }
    },
    methods: {
        convey(event) {
            this.$emit(event);
        }
    },
    watch: {
        
    },
}
</script>

<style scoped>
#logo {
    max-height: 60px;
    margin: 2px;
}
#search_window {
    max-height: 80px;
}
#header--middle {
    margin: 5px;
}
.v-col {
    margin-left: 2px;
    margin-right: 2px;
}
</style>
