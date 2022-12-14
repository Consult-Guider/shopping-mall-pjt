 <template>
    <v-container>
        <v-row>
            <v-col>
                <com_history_review :reviews="reviews" />
            </v-col>
        </v-row>
    </v-container>
 </template>
 
 <script>
 export default {
    data() {return {
        reviews: [
            {
                iid: 1234,
                iname: "LENOVO",
                user: {name: "이선한", option: "데스크탑"},
                rate: 4.5,
                createdAt: "20220102",
                content: "무난히 쓸만했습니당~",
                numRec: 24,
                numNotRec: 5,
            },
            {
                iid: 1234,
                iname: "LENOVO",
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