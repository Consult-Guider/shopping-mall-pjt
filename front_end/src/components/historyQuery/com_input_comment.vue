<template>
    <v-container>
        <v-row align="start">
            <v-divider class="my-2" />
            <v-col>
                <v-textarea
                v-model="text"
                variant="outlined" hide-details density="compact"
                prepend-icon="mdi-comment" auto-grow rows="2"
                @keydown.ctrl.enter="onClickPost" clearable
                ></v-textarea>
            </v-col>
            <v-col cols="auto">
                <v-btn
                color="primary"
                @click="onClickPost"
                >등록하기</v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
data() {
    return {
        text: "",
    }
},
methods: {
    onClickPost() {
        this.$emit("post", this.text);
    },
},
}
</script>

<style scoped>

</style>