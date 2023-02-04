<template>
    <v-container>
    <v-row id="wrapper">
        <v-col cols="auto">
            <v-combobox
                v-model="combobox.model"
                :items="combobox.items"
                :label="combobox.label"
                class="overflow-y-hidden" 
                density="compact" variant="plain" hide-details
            />
        </v-col>
        <v-col>
            <v-text-field
                v-model="text.model"
                :label="text.label"
                :append-inner-icon="text.icon"
                @click:appendInner="text.appendInner"
                @keydown.enter="text.appendInner"
                density="compact" variant="plain" hide-details
            />
        </v-col>
    </v-row>
    </v-container>
    
</template>

<script>
export default {
    data() {return {
        methods: this.$env.queryMethods,

        combobox: {
            model: this.$env.queryMethods.name.label, 
            label: null,
            items: [],
        },
        text: {
            model: undefined, 
            label: "찾고 싶은 상품을 검색해보세요!",
            icon: "mdi-magnify",
            appendInner: this.onClickIcon,
        },
    }},
    created() {
        this.combobox.items = Object.keys(this.methods).map(item => this.methods[item].label);
    },
    methods: {
        onClickIcon: function() {
            console.log("onClickIcon");
            this.$router.push({path: this.$endPoint.search, query: {keyword: this.text.model, method: this.find(this.combobox.model)},});
        },

        find(label) {
            const key = Object.keys(this.methods).find(key => this.methods[key].label == label);

            return this.methods[key].value;
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