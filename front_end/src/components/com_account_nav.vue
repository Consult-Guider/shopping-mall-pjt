<template>
    <v-card class="t2b size-limit" variant="flat">
        <div class="t2b pa-4 bg-nav" v-if="confirmAuth(['NULL'])">
            <h2 class="mb-4">로그인을 먼저 해주세요!</h2>

            <v-divider class="my-3" />
        </div>

        <div class="t2b pa-4 bg-nav" v-if="confirmAuth(['USER'])">
            <h2 class="mb-4">MY 쇼핑</h2>
            <p class="clickable link" @click="$router.push($endPoint.myPage);"
            >구매 조회</p>

            <v-divider class="my-3" />
        </div>

        <div class="t2b pa-4 bg-nav" v-if="confirmAuth(['SELLER', 'USER'])">
            <h2 class="mb-4">MY 활동</h2>
            <p class="clickable link" @click="$router.push($endPoint.historyQuery);"
            >문의내역 확인</p>
            <p class="clickable link" @click="$router.push($endPoint.historyReview);"
            >리뷰내역 확인</p>

            <v-divider class="my-3" />
        </div>

        <div class="t2b pa-4 bg-nav" v-if="confirmAuth(['SELLER', 'USER'])">
            <h2 class="mb-4">MY 정보</h2>
            <p class="clickable link" @click="$router.push($endPoint.updateAccount);"
            >개인정보확인/수정</p>

            <v-divider class="my-3" />
        </div>
        
        <div class="t2b pa-4 bg-nav" v-if="confirmAuth(['SELLER'])">
            <h2 class="mb-4">MY 판매</h2>
            <p class="clickable link" @click="$router.push($endPoint.createItem);"
            >상품 생성</p>

            <p class="clickable link" @click="$router.push($endPoint.myPage);"
            >구매 현황</p>

            <v-divider class="my-3" />
        </div>
    </v-card>
</template>

<script>
export default {
    methods: {
        confirmAuth(arr) {
            const role = this.$store.state.user.role;
            return arr.includes(role);
        },
    },
}
</script>

<style scoped>
.size-limit {
    /* width: 180px; */
    /* height: 600px; */
}
.link {
    color:darkblue;
    font-size: 12px;
}
</style>
