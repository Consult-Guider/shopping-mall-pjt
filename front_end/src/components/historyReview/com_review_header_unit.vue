<template>
    <v-container>
        <v-row>
            <v-col class="xgap">
                <v-btn
                density="compact"
                @click="linkTo(iid)"
                >상품명[{{iname}}]</v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col class="xgap">
                <v-btn class="update--btn"
                density="compact"
                @click="updateReview(rid)"
                >{{labelIsUpdating()}}</v-btn>
                <v-btn class="delete--btn"
                density="compact"
                @click="deleteReview(rid)"
                >삭제하기</v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        iid: Number,
        iname: String,
        rid: Number,
        user: Object,
        rate: Number,
        createdAt: String,
        content: String,
        numRec: Number,
        numNotRec: Number,

        isUpdating: Boolean,
    },
    methods: {
        labelIsUpdating() {
            return this.isUpdating ? "수정완료" : "수정하기"
        },

        linkTo(iid) {
            this.$router.push(this.$endPoint.item(iid));
        },
        updateReview(rid) {
            console.log("call updateReview: " + rid);
            this.$emit("updateReview", !this.isUpdating);
            // TODO: 해당 기능 axios로 연결하기.
        },
        deleteReview(rid) {
            console.log("call deleteReview: " + rid);
            // TODO: 해당 기능 axios로 연결하기.
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
