<template>
    <v-container>
        <v-row>
            <v-col cols="5">
                <v-card 
                elevation="5" 
                class="outer-shell overflow-auto"
                >
                    <v-item-group>
                        <v-item 
                        v-for="item in items" :key="item"
                        v-slot="{ isSelected, toggle }"
                        >
                            <v-card
                            :color="isSelected ? 'grey-lighten-2' : ''" @click="toggle"
                            class="d-flex align-center pa-3" 
                            >
                                <com_query_state v-bind=item :isSelected=isSelected @changeChat="onChangeChat" />
                            </v-card>
                        </v-item>
                    </v-item-group>
                </v-card>
            </v-col>
            <v-col>
                <v-card 
                elevation="5" 
                class="outer-shell overflow-auto"
                >
                    <com_query_chat :qid="qid" />
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    props: {
        items: Array,
    },
    data() {return {
        qid: null,
    }},
    methods: {
        onChangeChat(qid) {
            console.log("call onChangeChat" + ` ${qid}`);
            this.qid = qid;
        },
    },
}
</script>

<style scoped>
    .outer-shell {
        max-height: 500px;
    }
</style>