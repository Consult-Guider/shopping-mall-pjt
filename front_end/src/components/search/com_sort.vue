<template>
    <div class="t2b">
        <h3>{{ `"${keyword}"에 대한 검색 결과` }}</h3>

        <v-card
            class="my-5"
            variant="tonal"
        >
            <v-btn-toggle
                v-model="kind"
                color="primary" group
            >
                <v-btn
                    v-for="btn of btns" :key="btn"
                    :value="btn.value"
                >
                {{ btn.label }}
                </v-btn>
            </v-btn-toggle>
        </v-card>
    </div>
</template>

<script>
export default {
// props: {
//     keyword: String,
// },
data() {return {
    keyword:"컴퓨터",
    kind: "price-asc",
    btns: [
        {label: "낮은 가격순", value: "price-asc",},
        {label: "높은 가격순", value: "price-desc",},
        {label: "판매량순", value: "sales-desc",},
        {label: "최신순", value: "date-desc",},
    ],
}},
computed: {
    getKind() {return this.kind;}
},
watch: {
    getKind(val) {
        this.$emit("changeKind", val);
    },
},
}
</script>

<style scoped>

</style>