<template>
    <div class="t2b">
        <v-card
            class="my-5"
            variant="tonal"
        >
            <v-btn-toggle
                v-model="kind"
                color="primary" group
            >
                <v-btn
                    v-for="(btn, key) in btns" :key="btn"
                    :value="key"
                >
                {{ btn.label }}
                </v-btn>
            </v-btn-toggle>
        </v-card>
    </div>
</template>

<script>
export default {
props: {
    init: {
        type: String,
        default: "price-asc",
    },
    btns: {
        type: Object,
    },
},
data() {return {
    kind: this.init,
}},
computed: {
    getKind() {return this.kind;}
},
watch: {
    getKind(val) {
        this.$emit("update", this.btns[val].json);
    },
},
}
</script>

<style scoped>

</style>