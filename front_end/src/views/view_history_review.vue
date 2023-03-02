 <template>
    <v-container>
        <v-row>
            <v-col>
                <com_history_review :reviews="reviews" @reload="fetchReviewByUserId"/>
                <v-pagination
                v-model="pagination.page"
                :length="pagination.total"
                ></v-pagination>
            </v-col>
        </v-row>
    </v-container>
 </template>
 
 <script>
import { ErrRes, ReviewRes, PageReq } from '@/dto';

 export default {
    data() {return {
        pagination: {
            page: 1,
            total: 1,
        },

        reviews: [],
    }},
    methods: {
        fetchReviewByUserId: async function() {
            const page = PageReq.of(this.pagination.page-1, this.$env.view_history_reivew.numReviewOfOnePage);
            page.sort("createdAt", false);

            this.$auth.get(`/user/principal/review`, {params: page.params()}).then(res => {
                const response = ReviewRes.of(res);
                this.reviews = response.pages().map(this.transform);
                this.pagination.total = response.getTotalPages();
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
                rid: unit.rid,
                iid: unit.itemId,
                iname: unit.itemName,
                user: {id: unit.userId, name: unit.userName, option: unit.option},
                rate: unit.rating,
                createdAt: unit.createdAt,
                content: unit.content,
                numRec: 0,
                numNotRec: 0,
            };
        },
    },
    created() {
        this.fetchReviewByUserId();
    },
    computed: {
        page() {
            return this.pagination.page;
        },
    },
    watch: {
        page() {
            this.fetchReviewByUserId();
        },
    },
 }
 </script>
 
 <style scoped>
 
 </style>