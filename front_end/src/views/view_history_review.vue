 <template>
    <v-container>
        <v-row>
            <v-col>
                <com_account_header v-bind="state" />
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="auto">
                <com_account_nav />
            </v-col>
            <v-col>
                <com_history_review :reviews="reviews" />
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
        reviews: [
            {
                iid: 1234,
                user: {name: "이선한", option: "데스크탑"},
                rate: 4.5,
                createdAt: "20220102",
                content: "무난히 쓸만했습니당~",
                numRec: 24,
                numNotRec: 5,
            },
            {
                iid: 1234,
                user: {name: "저스틴 비버", option: "바지"},
                rate: 2.5,
                createdAt: "20221202",
                content: "별루임.",
                numRec: 2,
                numNotRec: 8,
            },
        ],
    }},
 }
 </script>
 
 <style scoped>
 
 </style>