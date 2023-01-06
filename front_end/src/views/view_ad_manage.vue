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
            {id: 1, startAt: "2015/01/01", endAt: "2015/01/10", name: "맥심 모카 골드", companyName: "Maxim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg", link: "https://www.coupang.com/vp/products/4550236145?itemId=9821768243&vendorItemId=71030128009&pickType=COU_PICK"},
            {id: 2, startAt: "2015/01/26", endAt: "2015/01/31", name: "맥심 모카 레드", companyName: "Midim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg", link: "https://www.coupang.com/vp/products/4550236145?itemId=9821768243&vendorItemId=71030128009&pickType=COU_PICK"},
            {id: 3, startAt: "2015/01/22", endAt: "2015/01/29", name: "맥심 모카 블루", companyName: "Mimim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg", link: "https://www.coupang.com/vp/products/4550236145?itemId=9821768243&vendorItemId=71030128009&pickType=COU_PICK"},
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