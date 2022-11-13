<template>
    <v-container>
    <v-row id="wrapper">
        <v-col cols="auto">
            <v-combobox
                v-model="combobox.model"
                :items="combobox.items"
                :label="combobox.label"
                class="overflow-y-hidden" clearable
                density="compact" variant="plain" hide-details
            />
        </v-col>
        <v-col>
            <v-text-field
                v-model="text.model"
                :label="text.label"
                :append-inner-icon="text.icon"
                @click:appendInner="text.appendInner"
                density="compact" variant="plain" hide-details
            />
        </v-col>
    </v-row>
    </v-container>
    
</template>

<script>
export default {
    data() {return {
        combobox: {
            model: undefined, 
            label: "전체",
            items: ["카테고리1", "카테고리2",],
        },
        text: {
            model: undefined, 
            label: "찾고 싶은 상품을 검색해보세요!",
            icon: "mdi-magnify",
            appendInner: this.onClickIcon,
        },
    }},
    methods: {
        onClickIcon: function() {
            console.log("onClickIcon");
            this.$router.push(this.$endPoint.search);
        },
    }
}
</script>

<style scoped>
#wrapper {
    height: 100%;
    padding-left:   2%;
    padding-right:  2%;

    border: 4px solid rgb(46, 199, 255);
}
.v-combobox {
    height: fit-content;
    min-width: 75px;

    border-radius: 0px;
}
.v-text-field {
    height: fit-content;

    border-radius: 0px;
}
.v-col {
    padding: 0px;
}
</style>