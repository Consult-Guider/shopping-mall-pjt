<template>
<v-container>
    <v-row justify="center">
        <v-col id="AD">
            <!-- 배너 광고 -->
            <com_img />
        </v-col>
    </v-row>
    <v-row justify="center">
        <v-col>
            전체 고객에 대한 추천 섹션
            <com_display />
        </v-col>
    </v-row>
    <v-row justify="center">
        <v-col>
            카테고리별 추천 섹션
            <com_display />
        </v-col>
    </v-row>
    <v-row justify="center">
        <v-col>
            고객별 맞춤 추천 섹션
            <com_display />
        </v-col>
    </v-row>
</v-container>
</template>

<script>
export default {
    data() {return {
        
    }},
    methods: {
        loadData() {
            console.log("load home");
        },
    },
    mounted() {
        this.loadData();
    }
}
</script>

<style scoped>
#AD {
    display: flex;
    justify-content: center;
    
    padding: 0px;

    background-color: grey;
}
#AD > * {
    width: 50vw;
}
</style>
