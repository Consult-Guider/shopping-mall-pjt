<template>
    <v-container>
        <v-row>
            <v-col>
                <com_account_header v-bind="state" />
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <com_orders :items="items" />
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
data() {return {
    state: {
        numReady: 1,
        numIng: 2,
        numDone: 3,
    },
    items: [
        {
            state: "ready",
            name: "에이원 강력먼지제거제",
            num: 2,
            price: 3000,
            img: "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/9b6c/b251918a3d0e8c81129c5db42d8fe524d35a171649af2017f172c85a8012.jpg",
        },
        {
            state: "ing",
            name: "에이원 강력먼지제거제",
            num: 2,
            price: 3000,
            img: "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/9b6c/b251918a3d0e8c81129c5db42d8fe524d35a171649af2017f172c85a8012.jpg",
        },
        {
            state: "done",
            name: "에이원 강력먼지제거제",
            num: 2,
            price: 3000,
            img: "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/vendor_inventory/9b6c/b251918a3d0e8c81129c5db42d8fe524d35a171649af2017f172c85a8012.jpg",
        },
    ],
}},
}
</script>

<style scoped>
</style>