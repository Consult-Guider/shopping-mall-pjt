<template>
    <v-container>
        <v-row align="center">
            <v-col cols="auto">
                <v-avatar color="primary" variant="flat">
                    {{$util.str2Avatar(item.companyName)}}
                </v-avatar>
            </v-col>
            <v-col>
                <div>{{item.name}}</div>
                <div class="text-grey">{{item.createdAt}}</div>
            </v-col>
            <v-col cols="auto">
                <div>{{item.state}}</div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        item: Object,

        isSelected: Boolean,
    },
    watch: {
        isSelected(val) {
            if(val) {
                this.$emit('changeChat', this.item);
            }
        },
    },
}
</script>

<style scoped>

</style>