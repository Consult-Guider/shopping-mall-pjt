 <template>
    <v-container>
        <v-row>
            <v-col>
                <com_account_header v-bind="state" />
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <com_exchanges :items="items" />
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
                exchangeAt: "20230402",
                orderedAt: "20230102",
                iid: 243,
                oid: 20230102,
                name: "에이원 강력먼지제거제",
                option: "23g 작은 통",
                num: 2,
                price: 3000,
            },
            {
                state: "ing",
                exchangeAt: "20230402",
                orderedAt: "20230102",
                iid: 243,
                oid: 20230102,
                name: "에이원 강력먼지제거제",
                option: "23g 작은 통",
                num: 2,
                price: 3000,
            },
            {
                state: "done",
                exchangeAt: "20230402",
                orderedAt: "20230102",
                iid: 243,
                oid: 20230102,
                name: "에이원 강력먼지제거제",
                option: "23g 작은 통",
                num: 2,
                price: 3000,
            },
        ],
    }},
 }
 </script>
 
 <style scoped>
 
 </style>