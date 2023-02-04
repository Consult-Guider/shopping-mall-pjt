<template>
    <div class="t2b pa-3">
        <!-- 프로필, 이름, 상품평 -->
        <div class="l2r">
            <v-avatar color="secondary" >
                <v-icon>mdi-account</v-icon>
            </v-avatar>
            <div class="t2b">
                <p>{{ $util.beAnonymous(user.name) }}</p>
                <div v-if="readonly">
                    <v-rating
                    v-model="data_modified.rate"
                    readonly size="x-small" density="compact"
                    ></v-rating>
                </div>
                <div v-else>
                    <v-rating
                    v-model="data_modified.rate"
                    size="x-small" density="compact"
                    ></v-rating>
                </div>
                <p>{{ $util.str2date(createdAt) }}</p>
            </div>
        </div>

        <!-- 구매 옵션 -->
        <v-spacer class="my-3" />
        <h5 class="text-grey">{{ user.option }}</h5>
        
        <!-- 내용 -->
        <v-spacer class="my-3" />
        <div v-if="readonly">
            <p>{{ content }}</p>
        </div>
        <div v-else>
            <v-text-field
            density="compact" variant="outlined"
            v-model="data_modified.content"
            ></v-text-field>
        </div>

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

        <v-spacer class="my-3" v-if="isThisYours"/>

        <div class="l2r" v-if="isThisYours">
            <v-btn
                density="compact"
                :color="btnUpdate.color"
                @click="btnUpdate.click"
                class="text-white"
            >{{ btnUpdate.label }}</v-btn>
            <v-btn
                density="compact"
                :color="btnDelete.color"
                @click="btnDelete.click"
                class="text-white"
            >{{ btnDelete.label }}</v-btn>
        </div>
    </div>
</template>

<script>
import { ReviewSelected } from '@/dto/util';

export default {
    props: {
        rid: String,
        user: Object,
        rate: Number,
        createdAt: String,
        content: String,
        numRec: Number,
        numNotRec: Number,

        readonly: {
            default: true,
            type: Boolean,
        },  
    },
    data() {return {
        data_modified: {
            rate: this.rate,
            createdAt: this.createdAt,
            content: this.content,
        },

        btnRec: {
            label: "추천", 
            click: this.onClickRec,
        },
        btnNoRec: {
            label: "비추천", 
            click: this.onClickNoRec,
        },

        btnUpdate: {
            label: "수정하기", 
            click: this.onClickUpdate,
            color: "blue",
            value: false,
        },
        btnDelete: {
            label: "삭제하기", 
            click: this.onClickDelete,
            color: "red",
        },
    }},
    methods: {
        onClickRec() {
            console.log("click onClickRec");
        },
        onClickNoRec() {
            console.log("click onClickNoRec");
        },

        onClickUpdate() {
            this.btnUpdate.value = !this.btnUpdate.value;

            let data;
            if(this.btnUpdate.value) {
                this.btnUpdate.label = "취소하기";
                this.btnUpdate.color = "grey";
                data = ReviewSelected.of(this.rid, this.data_modified.rate, this.data_modified.content).json();
            } else {
                this.btnUpdate.label = "수정하기";
                this.btnUpdate.color = "blue";
                data = ReviewSelected.empty();
            }
            this.$emit('doSelect', data);
        },
        onClickDelete() {
            this.$emit('delete', this.rid);
        },
    },
    watch: {
        readonly(val) {
            if(val) {
                console.log("emit update");
                this.$emit("update", this.data_modified);
            }
        },
    },
    computed: {
        isThisYours() {
            return this.$store.getters.isUser && this.user.id == this.$store.state.user.id;
        },
    },
}
</script>

<style scoped>

</style>
