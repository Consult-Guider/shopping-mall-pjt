<template>
    <v-carousel
        cycle interval="5000"
        show-arrows="hover" hide-delimiter-background
        v-model="page"
        height="auto"
    >
        <v-carousel-item 
            v-for="p of $util.range(caluated.pages)" :key="p"
        >
            <v-sheet class="fill">
                <v-container class="fill y-gap">
                    <v-row 
                        v-for="r of $util.range(rows)" :key="r"
                    >
                        <v-col 
                            v-for="c of $util.range(cols)" :key="c"
                            class="y-gap"
                        >
                            <com_item :data="getItem(p, r, c)" />
                        </v-col>
                    </v-row>
                </v-container>
            </v-sheet>
        </v-carousel-item>
    </v-carousel>
</template>

<script>
export default {
    props: {
        rows: {
            type: Number,
            default: 2,
        },
        cols: {
            type: Number,
            default: 3,
        },
        items: {
            type: Array,
            default: new Array(),
        },
    },
    data() {return {
        page: 0,
        data: {
            items: this.items,
            mockItem: null
        },
        caluated: {
            pages: 0,
            items: [],
        }
    }},
    methods: {
        setPagesNItems() {
            this.caluated.pages = this.$util.getPages(this.rows, this.cols, this.data.items);
            this.caluated.items = this.$util.makeFrame(this.rows * this.cols, this.data.items, this.data.mockItem);
        },
        getItem(page, row, col) {
            return this.caluated.items[this.$util.getIdx(page, row, col, this.rows, this.cols)]
        },
    },
    watch: {
        items(val) {
            this.data.items = val;
            this.setPagesNItems();
        },
    },
    mounted() {
        this.setPagesNItems();
    },
}
</script>

<style scoped>

</style>