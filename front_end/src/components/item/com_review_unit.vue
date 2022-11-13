<template>
    <div class="t2b pa-3">
        <!-- 프로필, 이름, 상품평 -->
        <div class="l2r">
            <v-avatar color="secondary" >
                <v-icon>mdi-account</v-icon>
            </v-avatar>
            <div class="t2b">
                <p>{{ $util.beAnonymous(user.name) }}</p>
                <v-rating
                v-model="rate_model"
                readonly size="x-small" density="compact"
                ></v-rating>
                <p>{{ $util.str2date(createdAt) }}</p>
            </div>
        </div>

        <!-- 구매 옵션 -->
        <v-spacer class="my-3" />
        <h5 class="text-grey">{{ user.option }}</h5>
        
        <!-- 내용 -->
        <v-spacer class="my-3" />
        <p>{{ content }}</p>

        <v-spacer class="my-3" />

        <!-- 추천 비추천 버튼 -->
        <div class="l2r">
            <v-btn
                density="compact" variant="outlined"
                color="primary"
                @click="btnRec.click"
            >{{ btnRec.label }}</v-btn>

            <h3 class="mx-5">{{ `${ numRec-numNotRec }` }}</h3>

            <v-btn
                density="compact" variant="outlined"
                color="primary"
                @click="btnNoRec.click"
            >{{ btnNoRec.label }}</v-btn>
        </div>

    </div>
</template>

<script>
export default {
    props: {
        user: Object,
        rate: Number,
        createdAt: String,
        content: String,
        numRec: Number,
        numNotRec: Number,
    },
    data() {return {
        rate_model: 0,

        btnRec: {
            label: "추천", 
            click: this.onClickRec,
        },
        btnNoRec: {
            label: "비추천", 
            click: this.onClickNoRec,
        }
    }},
    methods: {
        onClickRec() {
            console.log("click onClickRec");
        },
        onClickNoRec() {
            console.log("click onClickNoRec");
        },
    },
    mounted() {
        this.rate_model = this.rate;
    },

}
</script>

<style scoped>

</style>
