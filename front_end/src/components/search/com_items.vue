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
                    <com_item :data="getItem(1, r, c)" />
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
    },
    data() {return {
        data: {
            items: [1,2,3,4,5,6,7,8,9,10,11,12,13],
            mockItem: null
        },
        caluated: {
            rows: [],
            items: [],
        }
    }},
    methods: {
        setPagesNItems() {
            this.caluated.rows = this.$util.getPages(1, this.cols, this.data.items);
            this.caluated.items = this.$util.makeFrame(this.cols, this.data.items, this.data.mockItem);
        },
        getItem(page, row, col) {
            return this.caluated.items[this.$util.getIdx(page, row, col, this.rows, this.cols)]
        },
    },
    mounted() {
        this.setPagesNItems();
    },
}
</script>

<style scopde>

</style>