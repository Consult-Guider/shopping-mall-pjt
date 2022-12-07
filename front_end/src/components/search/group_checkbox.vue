<template>
    <div class="t2b pa-2">
        <h4 class="my-2">{{ name }}</h4>

        <v-checkbox
            v-for="option of options" :key="option"

            class="mb-n2" hide-details density="compact"
            :label="option.label"
            v-model="option.default"
        ></v-checkbox>

        <v-divider class="my-3" />
    </div>
</template>

<script>
export default {
    props: {
        name: String,
        options: Array,
    },
    computed: {
        watcherOptions() {
            let result = new Map();
            for(const option of this.options) {
                result.set(option.value, option.default);
            }
            return result;
        }
    },
    watch: {
        watcherOptions(val) {
            this.$emit("changeOptions", val);
        }
    },
}
</script>

<style scoped>

</style>