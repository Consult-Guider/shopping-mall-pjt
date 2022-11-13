<template>
    <v-container>
        <v-row>
            <v-col cols="auto">
                <com_filter />
            </v-col>
            <v-col>
                <div class="t2b">
                    <com_sort />

                    <com_items v-bind="items" />

                    <v-pagination
                    v-model="pagination.page"
                    :length="pagination.total"
                    ></v-pagination>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
data() {return {
    pagination: {
        page: 1,
        total: 10,
    },
    items: {
        cols: 4,
    }
}},
}
</script>

<style>

</style>
Category_items