<template>
    <v-container>
        <v-row>
            <v-col>
                <com_ad_manage :data="tray" @fetchAdData="fetchAdData"/>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { ErrRes, AdImgRes, PageReq } from '@/dto';

export default {
    data() {
        return {
            tray: [
            {id: 1, createdAt: 20150101, termDay: 30, name: "맥심 모카 골드", companyName: "Maxim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg"},
            {id: 2, createdAt: 20150133, termDay: 10, name: "맥심 모카 레드", companyName: "Midim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg"},
            {id: 3, createdAt: 20150122, termDay: 20, name: "맥심 모카 블루", companyName: "Mimim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg"},
        ],
        }
    },
    methods: {
        fetchAdData: async function() {
            const page = PageReq.of(0, 10).sort('createdAt');
            this.$auth.get('/adimg', {params: page.params()})
            .then(res => {
                this.tray = AdImgRes.of(res).pages();
            })
            .catch(err => {
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
    created() {
        // this.fetchAdData();
    },
}
</script>

<style scoped>

</style>