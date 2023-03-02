<template>
<v-container>
    <v-row justify="center">
        <v-col id="AD">
            <!-- 배너 광고 -->
            <com_img :src="bannerSrc" :link="bannerLink"/>
        </v-col>
    </v-row>
    <v-row justify="center"
        v-for="(val, key) in tags" :key="key"
    >
        <v-col>
            <com_display :name="key" :items="val" />
        </v-col>
    </v-row>
</v-container>
</template>

<script>
import { ErrRes, AdImgRecommendRes, PageReq, TagRes, ItemSearchedRes } from '@/dto';

export default {
    data() {return {
        trayAdBanner: null,

        tags: {},
    }},
    computed: {
        bannerSrc() {
            return this.trayAdBanner ? this.trayAdBanner.src : null;
        },
        bannerLink() {
            return this.trayAdBanner ? this.trayAdBanner.link : null;
        },
    },
    methods: {
        loadAdBanner: async function() {
            this.$http.get(`/adimg/recommend`).then(res => {
                const response = AdImgRecommendRes.of(res);

                this.trayAdBanner = response.json();
            }).catch(err => {
                const errorCode = ErrRes.of(err).errorCode;

                // 에러 메세지 표시.
                switch(errorCode) {
                    default:
                        alert(errorCode);
                        break;
                }
            });
        },
        fetchRandomTags() {
            const page = PageReq.of(0, 3);
            this.$http.get(`/tag`, {params: page.params()}).then(res => {
                const response = TagRes.of(res);
                for(const tag of response.pages()) {
                    const tagName = tag.name;
                    this.fetchItemsByTag(tagName);
                }
            }).catch(err => {
                const errorCode = ErrRes.of(err).errorCode;

                // 에러 메세지 표시.
                switch(errorCode) {
                    default:
                        alert(errorCode);
                        break;
                }
            });
        },
        fetchItemsByTag(tagName) {
            const page = PageReq.of(0, 18);
            this.$http.get(`/tag/${tagName}/item`, {params: page.params()}).then(res => {
                const response = ItemSearchedRes.of(res);
                this.tags[tagName] = response.pages();
            }).catch(err => {
                const errorCode = ErrRes.of(err).errorCode;

                // 에러 메세지 표시.
                switch(errorCode) {
                    default:
                        alert(errorCode);
                        break;
                }
            });
        },
    },
    mounted() {
        this.loadAdBanner();
        this.fetchRandomTags();
    }
}
</script>

<style scoped>
#AD {
    display: flex;
    justify-content: center;
    
    padding: 0px;

    background-color: grey;
}
#AD > * {
    width: 50vw;
}
</style>
