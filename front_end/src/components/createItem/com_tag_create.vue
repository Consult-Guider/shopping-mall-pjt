<template>
    <v-container>
        <v-row>
            <v-col>
                <v-card class="tagDisplay overflow-y-auto pa-3" height="100">
                    <v-chip-group column>
                        <v-chip 
                        v-for="tag in tagList" :key="tag"
                        closable label
                        @click:close="deleteSet(tag)"
                        >{{tag}}</v-chip>
                    </v-chip-group>
                </v-card>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <com_input_comment @post="appendSet"/>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
data() {
    return {
        tagList: new Set(),

        text: "",
    }
},
methods: {
    emitList() {
        let tagList = Array.from(this.tagList);
        this.$emit("update", tagList);
    },

    appendSet(tag) {
        this.tagList.add(tag);
        this.emitList();
    },
    deleteSet(tag) {
        this.tagList.delete(tag);
        this.emitList();
    },
},
}
</script>

<style scoped>
    .tagDisplay {
        background-color: aquamarine;
    }
</style>