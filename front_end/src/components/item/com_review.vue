<template>
    <div class="t2b">
        <!-- 별점 -->
        <h3>상품평</h3>
        <v-spacer class="my-2" />
        <p>동일한 상품에 대해 작성된 상품평으로, 판매자는 다를 수 있습니다.</p>
        <v-spacer class="my-3" />

        <div class="l2r">
            <v-rating
            v-model="rate"
            readonly
            ></v-rating>

            <h1 class="mx-5">{{ `평균 ${rate} 점 | 총 ${numRate} 개` }}</h1>

            <v-spacer />

        </div>

        <v-spacer class="my-3" />

        <!-- 상품평 작성 -->
        <com_review_posting v-bind="reviewSelected" @update="updateReviewPosting"/>

        <!-- 상품평 검색 -->
        <v-card 
            variant="plain" class="bg-grey"
        >
            <v-card-text class="l2r">
                <v-btn-toggle
                v-model="sort"
                color="primary" variant="plain" divided
                >
                    <v-btn v-for="(val, key) in sortBtn" :key="key"
                    :value="val.value">{{ val.label }}</v-btn>
                </v-btn-toggle>

                <v-text-field
                    variant="outlined" density="compact" 
                    append-inner-icon="mdi-magnify" bg-color="white"
                    :label="search.label" class="mx-5"
                    v-model="search.value"
                    @click:append-inner="search.click"
                    hide-details
                ></v-text-field>

                <v-spacer />
            </v-card-text>
        </v-card>

        <v-spacer class="my-3" />
        
        <!-- 상품평 -->
        <div v-for="review of reviews" :key="review">
            <com_review_unit v-bind="review" @doSelect="setReviewSelected" @delete="deleteReviewPosting"/>
            <v-divider class="my-3" />
        </div>
        
        <!-- 페이지네이션 -->
        <v-pagination
        v-model="page"
        :length="total"
        ></v-pagination>

    </div>
</template>

<script>
import { ErrRes, ReviewReq, PageReq, ReviewRes, ReviewStatRes } from '@/dto';
import { ReviewSelected } from '@/dto/util';

export default {
props: {
    item: Object,
},
computed: {
    iid() {
        return this.item.iid;
    },
    reviews() {
        return this.item.reviews?.map(this.transform);
    },
},
data() {return {
    page: 1,
    total: 1,
    sort: "rate-desc",

    sortBtn: {
        rate: {label: "평점순", value: "rate-desc", json: {trg: "rating", isAcs: false,}},
        date: {label: "최신순", value: "date-desc", json: {trg: "createdAt", isAcs: false,}},
    },

    reviewSelected: ReviewSelected.empty(),

    rate: 0,
    numRate: 0,

    search: {
        value: "",
        label: "상품평 검색",
        click: this.onClickSearch,
    },
}},
methods: {
    onClickSearch() {
        // 검색할 키워드가 없으면 그냥 로드.
        if(!this.search.value) {
            this.fetchReviewPosting();
        } else {
            this.fetchReviewSearched(this.search.value);
        }
    },

    setReviewSelected(val) {
        this.reviewSelected = val;
    },

    find(value) {
        const method = this.sortBtn;
        return Object.keys(method).find(key => method[key].value == value);
    },
    findJson(value) {
        const method = this.sortBtn;
        return method[this.find(value)].json;
    },

    // 리뷰 Create & Update
    updateReviewPosting: async function(payload) {
        const text = payload.text;
        const rating = payload.rating;

        if(!text) {
            alert("리뷰내용을 작성해주세요!!");
            return ;
        }

        const data = ReviewReq.of(rating, text).json();
        let promise;
        if(this.reviewSelected.rid) {
            promise = this.$auth.put(`review/${this.reviewSelected.rid}`, data);
        } else {
            promise = this.$auth.post(`item/${this.iid}/review`, data);
        }

        promise.then(() => {
            // 입력창 초기화
            this.reviewSelected = ReviewSelected.empty();
            
            // 변경된 데이터에 맞춰 데이터 재로드.
            this.fetchReviewPosting();
        }).catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                case "TRANSACTION_NOT_FOUNDED":
                    alert("해당 상품을 구매하신 적이 없습니다.\n구매자만 리뷰를 남길 수 있습니다.");
                    break;
                case "ONLY_USER_CAN_ACCESS":
                    alert("리뷰는 소비자만 사용할 수 있는 기능입니다. 소비자로써 로그인 해주십시오.");
                    break;
                default:
                    alert(errorCode);
                    break;
            }
        })
    },

    // Delete Review By ReviewId
    deleteReviewPosting: async function(rid) {
        this.$auth.delete(`/review/${rid}`)
        .then(() => {
            // 변경된 데이터에 맞춰 데이터 재로드.
            this.fetchReviewPosting();
        })
        .catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                default:
                    alert(errorCode);
                    break;
            }
        })
    },


    // Review Read By ItemId
    fetchReviewPosting: async function() {
        const json = this.findJson(this.sort);
        const page = PageReq.of(this.page-1, this.$env.com_reivew.numReviewOfOnePage).sort(json.trg, json.isAcs).params();

        this.$http.get(`item/${this.iid}/review`, {params: page})
        .then((res) => {
            const response = ReviewRes.of(res);
            // 페이지 바의 전체 페이지 갱신.
            this.total = response.getTotalPages();
            // 평점 총 갯수 갱신.
            this.numRate = response.getTotalElements();
            // 모든 평점의 평균 계산. 
            if(response.getTotalElements()) {
                this.fetchReviewStat();
            }
            // 로드한 데이터 업데이트.
            this.$emit("set", response.pages());
        })
        .catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                default:
                    alert(errorCode);
                    break;
            }
        })
    },

    // Review Average Read By ItemId
    fetchReviewStat: async function() {
        this.$http.get(`item/${this.iid}/review/statistics`)
        .then((res) => {
            const payload = ReviewStatRes.of(res).json();
            // 로드한 평점 평균 업데이트.
            this.rate = payload.avg?.toFixed(2);
        })
        .catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                default:
                    alert(errorCode);
                    break;
            }
        })
    },

    // Review Read By Keyword
    fetchReviewSearched: async function(keyword) {
        const json = this.findJson(this.sort);
        const page = PageReq.of(this.page-1, 5).sort(json.trg, json.isAcs);

        this.$http.get(`/review`, {params: {
                keyword: keyword,
                ...page.params()
        },})
        .then((res) => {
            const payload = ReviewRes.of(res).pages();
            // 로드한 데이터 업데이트.
            this.$emit("set", payload);
        })
        .catch(err => {
            const errorCode = ErrRes.of(err).errorCode;

            // 에러 메세지 표시.
            switch(errorCode) {
                default:
                    alert(errorCode);
                    break;
            }
        })
    },

    // 해당 페이지를 위한 추가적인 데이터 변경.
    transform(unit) {
        return {
            rid: unit.rid,
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
    this.fetchReviewPosting();
},
watch: {
    sort() {
        this.fetchReviewPosting();
    },
    page() {
        this.fetchReviewPosting();
    },
},
}
</script>

<style scoped>

</style>
