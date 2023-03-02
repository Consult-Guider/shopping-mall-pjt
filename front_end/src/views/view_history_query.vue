 <template>
    <v-container>
        <v-row>
            <v-col>
                <com_history_query :items="items" :totalPages="totalPages" @updatePages="fetchMyQuestion"/>
            </v-col>
        </v-row>
    </v-container>
 </template>
 
 <script>
import { ErrRes, QnARes, PageReq } from '@/dto';

 export default {
    data() {return {
        totalPages: 1,

        items: [],
    }},
    created() {
        this.fetchMyQuestion(0);
    },
    methods: {
        fetchMyQuestion: async function(numPage) {
            const page = PageReq.of(numPage-1, 5).sort('createdAt', false);

            this.$auth.get(`/user/principal/question`, {params: page.params()}).then(res => {
                const response = QnARes.of(res);

                this.items = response.pages().map(this.transform);
                this.totalPages = response.getTotalPages();
            }).catch(err => {
                const errorCode = ErrRes.of(err).errorCode;

                // 에러 메세지 표시.
                switch(errorCode) {
                    default:
                        alert(errorCode);
                        break;
                }
            });
        },
        transform(unit) {
            return {
                state: "",
                createdAt: unit.createdAt,
                qid: unit.qid,
                name: unit.itemName,
                companyName: unit.companyName,
                content: unit.content,

                user: unit.user,
                kind: unit.kind,
            };
        },
    },
 }
 </script>
 
 <style scoped>
 
 </style>