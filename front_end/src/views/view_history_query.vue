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
                <com_history_query :items="items" />
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
                state: "답변 완료",
                createdAt: "20230402",
                qid: 20230102,
                name: "에이원 강력먼지제거제",
                companyName: "coupang",
            },
            {
                state: "답변 완료",
                createdAt: "20230402",
                qid: 2023415,
                name: "에이원 강력먼지제거제",
                companyName: "coupang",
            },
            {
                state: "답변 완료",
                createdAt: "20230402",
                qid: 2352356,
                name: "에이원 강력먼지제거제",
                companyName: "coupang",
            },
            {
                state: "답변 완료",
                createdAt: "20230402",
                qid: 20230102,
                name: "에이원 강력먼지제거제",
                companyName: "coupang",
            },
            {
                state: "답변 완료",
                createdAt: "20230402",
                qid: 2023415,
                name: "에이원 강력먼지제거제",
                companyName: "coupang",
            },
            {
                state: "답변 완료",
                createdAt: "20230402",
                qid: 2352356,
                name: "에이원 강력먼지제거제",
                companyName: "coupang",
            },
        ],
    }},
 }
 </script>
 
 <style scoped>
 
 </style>