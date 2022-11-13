<template>
    <div class="t2b pa-3">
        <!-- 프로필, 이름, 상품평 -->
        <div class="l2r">
            <v-avatar color="secondary" rounded="0" >
                <p :style="style">{{ tag }}</p>
            </v-avatar>
            <div class="t2b">
                <p>{{ $util.beAnonymous(user.name) }}</p>
                
                <h5 class="text-grey">{{ user.option }}</h5>
            </div>

            <v-spacer />

            <p>{{ $util.str2date(createdAt) }}</p>
        </div>
        
        <!-- 내용 -->
        <v-spacer class="my-3" />
        <p>{{ content }}</p>

        <v-spacer class="my-3" />
    </div>
</template>

<script>
export default {
props: {
    user: Object,
    kind: String,
    createdAt: String,
    content: String,
},
data() {return {
    // user: {name: "이선영", option: "데스크탑"},
    // kind: "Q",
    // createdAt: "20221204",
    // content: "윈도우는 깔려서 오는 건가요?",
}},
methods: {
    isState(kind) {
        return (kind == "Q");
    },
},computed: {
    style() {
        const color = this.isState(this.kind) ? "green" : "red";
        return `color: ${color};`;
    },
    tag() {
        const color = this.isState(this.kind) ? "질문" : "답변";
        return `[${color}]`;
    },
},
}
</script>

<style scoped>

</style>
