<template>
    <v-container>
        <v-row align="center">
            <v-col cols="auto">
                <v-avatar color="primary" variant="flat">
                    {{$util.str2Avatar(companyName)}}
                </v-avatar>
            </v-col>
            <v-col>
                <div>{{name}}</div>
                <div class="text-grey">{{createdAt}}</div>
            </v-col>
            <v-col cols="auto">
                <div>{{state}}</div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        state: String,
        createdAt: String,
        qid: Number,
        name: String,
        companyName: String,
        isSelected: Boolean,
    },
    data() {return {
        
    }},
    methods: {
        
    },
    watch: {
        isSelected(val) {
            if(val) {
                this.$emit('changeChat', this.qid);
            }
        },
    },
}
</script>

<style scoped>

</style>