<template>
    <v-container>
        <v-row>
            <v-col class="xgap">
                <v-btn
                density="compact"
                @click="linkTo(review.iid)"
                >상품명[{{review.iname}}]</v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <com_review_unit v-bind="review" @doSelect="setReviewSelected" @delete="deleteReview"/>
            </v-col>
        </v-row>
        <v-row v-if="isUpdating">
            <v-col>
                <com_review_posting v-bind="reviewSelected" @update="updateReview"/>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { ErrRes, ReviewReq } from '@/dto';
import { ReviewSelected } from '@/dto/util';

export default {
    props: {
        review: Object,
    },
    data() {
        return {
            isUpdating: false,

            reviewSelected: ReviewSelected.of(this.review.rid, this.review.rate, this.review.content),
        }
    },
    methods: {
        setReviewSelected(val) {
            this.isUpdating = val.rid;
        },

        linkTo(iid) {
            this.$router.push(this.$endPoint.item(iid));
        },

        updateReview(payload) {
            const rid = this.reviewSelected.rid;
            const text = payload.text;
            const rating = payload.rating;
            
            if(!text) {
                alert("리뷰내용을 작성해주세요!!");
                return ;
            }
            
            const data = ReviewReq.of(rating, text).json();
            this.$auth.put(`/review/${rid}`, data).then(() => {
                this.$emit("reload");
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
        deleteReview(rid) {
            this.$auth.delete(`/review/${rid}`)
            .then(() => {
                // 변경된 데이터에 맞춰 데이터 재로드.
                this.$emit("reload");
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
    },
}
</script>

<style scoped>
  .xgap > * {
    margin-right: 10px;
  }
  .update--btn {
    background-color: grey;
    color: white;
  }
  .delete--btn {
    background-color: red;
    color: white;
  }
</style>
