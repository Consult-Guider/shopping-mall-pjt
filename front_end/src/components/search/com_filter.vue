<template>
    <v-card class="size-limit overflow-y-auto" variant="outlined">
        <group_checkbox 
            v-for="option of filter_options" :key="option"
            v-bind="option"
            @changeOptions="(obj) => updateOptions(obj)"
        />
    </v-card>
</template>

<script>
export default {
data() {return {
    filters: new Map(),

    filter_options: [
        {
            name: "생활 용품",
            options: [
                {
                    'label': "컴퓨터", 
                    'value': "computer", 
                    'default': 'false',
                },
                {
                    'label': "셔츠", 
                    'value': "shirt", 
                    'default': 'false',
                },
                {
                    'label': "포장지", 
                    'value': "wrapper", 
                    'default': 'false',
                },
            ],
        },
        {
            name: "컴퓨터 용품",
            options: [
                {
                    'label': "GPU", 
                    'value': "gpu", 
                    'default': 'false',
                },
                {
                    'label': "CPU", 
                    'value': "cpu", 
                    'default': 'false',
                },
                {
                    'label': "RAM", 
                    'value': "ram", 
                    'default': 'false',
                },
            ],
        },
    ],
    
}},
methods: {
    updateOptions(obj) {
        this.filters = new Map([...this.filters, ...obj]);
    },
},
}
</script>

<style scoped>
.size-limit {
    height: 700px;
    width: 200px;
}
</style>