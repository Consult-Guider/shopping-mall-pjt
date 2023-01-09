<template>
<v-container>
    <v-row justify="center">
        <v-col id="AD">
            <!-- 배너 광고 -->
            <com_img :src="bannerSrc" :link="bannerLink"/>
        </v-col>
    </v-row>
    <v-row justify="center">
        <v-col>
            전체 고객에 대한 추천 섹션
            <com_display />
        </v-col>
    </v-row>
    <v-row justify="center">
        <v-col>
            카테고리별 추천 섹션
            <com_display />
        </v-col>
    </v-row>
    <v-row justify="center">
        <v-col>
            고객별 맞춤 추천 섹션
            <com_display />
        </v-col>
    </v-row>
</v-container>
</template>

<script>
import { ErrRes, AdImgRecommendRes } from '@/dto';

export default {
    data() {return {
        trayAdBanner: null,
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
    },
    mounted() {
        this.loadAdBanner();
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
