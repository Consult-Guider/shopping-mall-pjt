<template>
    <v-sheet class="fill">
        <v-container class="fill y-gap">
            <v-row 
                v-for="r of $util.range(caluated.rows)" :key="r"
            >
                <v-col 
                    v-for="c of $util.range(cols)" :key="c"
                    class="y-gap"
                >
                    <com_item :data="getItem(0, r, c)" />
                </v-col>
            </v-row>
        </v-container>
    </v-sheet>
</template>

<script>
export default {
    props: {
        cols: {
            type: Number,
            default: 5,
        },
        data: {
            type: Array,
        },
    },
    data() {return {
        mockItem: null,
        caluated: {
            rows: 1,
            items: [],
        }
    }},
    methods: {
        setPagesNItems() {
            this.caluated.rows = this.$util.getPages(1, this.cols, this.data);
            this.caluated.items = this.$util.makeFrame(this.cols, this.data, this.mockItem);
        },
        getItem(page, row, col) {
            return this.caluated.items[this.$util.getIdx(page, row, col, this.caluated.rows, this.cols)]
        },
    },
    watch: {
        data() {
            this.setPagesNItems();
        },
    },
}
</script>

<style scopde>

</style>