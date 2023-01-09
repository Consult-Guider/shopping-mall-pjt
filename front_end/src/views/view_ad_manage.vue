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
            tray: [],
        }
    },
    methods: {
        fetchAdData: async function() {
            const page = PageReq.of(0, 5);
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
        this.fetchAdData();
    },
}
</script>

<style scoped>

</style>