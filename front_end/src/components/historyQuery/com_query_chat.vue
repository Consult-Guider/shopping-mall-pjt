<template>
    <v-container v-if="(chats.length)">
        <v-row v-for="chat in chats" :key="chat">
            <v-col>
                <com_query_unit v-bind="chat" isAnswer />
            </v-col>
        </v-row>
    </v-container>
    <v-container v-else>
        <v-row>
            <v-col>
                <h1 class="text-center">선택된 문의 내역이 없습니다.</h1>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        chats: Array,
    },
}
</script>

<style scoped>

</style>