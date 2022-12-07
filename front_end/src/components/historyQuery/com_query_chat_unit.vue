<template>
    <v-card
    class="pa-4"
    :color="isAnswer(state) ? 'grey-lighten-2' : ''"
    >
    <strong>
        {{state}}
    </strong>
    <p>
        {{content}}
    </p>
    <p class="text-grey">
        {{createdAt}}
    </p>
    </v-card>
</template>

<script>
export default {
    props: {
        state: String,
        content: String,
        createdAt: String,
    },
    methods: {
        isAnswer(state) {
            return state=="Answer";
        },
    },
}
</script>

<style scoped>

</style>