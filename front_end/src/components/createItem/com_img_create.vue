<template>
    <v-img
        :src="Src" 
    >
        <div
        class="v-card--cover-up text-h2"
        >
            <label class="clickable">
                <v-icon>mdi-image</v-icon>
                <v-file-input v-show="false" multiple accept="image/*" v-model="src" />
            </label>
            <p>사진 삽입</p>
        </div>
        <div class="v-card--delete" v-if="!disabled">
            <v-btn @click="onClickDelete"
            ><v-icon>mdi-delete-empty</v-icon></v-btn>
        </div>
    </v-img>
</template>

<script>
export default {
props: {
    disabled: {
        type: Boolean,
        default: false,
    },
    img: {
        type: String,
        default: undefined,
    },
    always_empty: {
        type: Boolean,
        default: false,
    },
},
data() {return {
    src: undefined,
    url: this.img,
}},
methods: {
    onClickDelete() {
        this.$emit("deleteImg", this.url);
        this.src = undefined;
    },

    transImg(file) {
        if(file == null) { return; }
        return URL.createObjectURL(file[0]);
    },
},
computed: {
    Src() { return this.url ?? this.$defaults.image; },
},
watch: {
    src() { 
        const old_one = this.url;
        const url = this.src ? this.transImg(this.src) : null;
        this.url = this.always_empty ? null : url
        this.$emit("updateImg", old_one, url);
    },
},
}
</script>

<style scoped>
    img {
        object-fit: contain;
    }

    .v-card--cover-up {
        position: absolute;
        top: 50%; left: 50%; transform: translate(-50%, -50%);

        display: flex; flex-direction: column;
        align-items: center; justify-content: center;

        background-color: grey; opacity: .3;
        color: white;

        height: 100%; width: 100%;

        text-align: center;
    }

    .v-card--delete {
        position: absolute;
        top: 2%; right: 2%; 

        display: flex;
        align-items: center; justify-content: center;
    }
</style>