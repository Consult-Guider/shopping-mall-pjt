<template>
    <v-container>
        <v-row align="center">
            <v-col>
                <p id="des">
                    {{description.label}}
                </p>
            </v-col>
            <v-col id="avatar-container" cols="auto">
                <v-avatar class="clickable" @click="avatarFacebook.click">
                    <v-img :src="avatarFacebook.image"/>
                </v-avatar>
                <v-avatar class="clickable" @click="avatarBlog.click">
                    <v-img :src="avatarBlog.image"/>
                </v-avatar>
                <v-avatar class="clickable" @click="avatarInsta.click">
                    <v-img :src="avatarInsta.image"/>
                </v-avatar>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {return {
        description: {
            label: "\
            사이버몰 내 판매되는 상품 중에는 쿠팡에 등록한 개별 판매자가 판매하는 마켓플레이스(오픈마켓) 상품이 포함되어 있습니다. \
            마켓플레이스(오픈마켓) 상품의 경우 쿠팡은 통신판매중개자이며 통신판매의 당사자가 아닙니다. \
            쿠팡은 마켓플레이스(오픈마켓) 상품, 거래정보 및 거래 등에 대하여 책임을 지지 않습니다. \
            쿠팡은 소비자 보호와 안전거래를 위해 신뢰관리센터(CM112@coupang.com)를 운영하고 있으며, 관련 분쟁이 발생할 경우 별도의 분쟁 처리절차에 의거 분쟁이 처리됩니다. \
            ",
        },
        avatarFacebook: {
            image: "https://png.pngtree.com/element_our/png_detail/20181011/facebook-social-media-icon-design-template-vector-png_127003.png",
            click: this.onClickFacebook,
        },
        avatarBlog: {
            image: "https://png.pngtree.com/element_our/sm/20180626/sm_5b32227e9386b.jpg",
            click: this.onClickBlog,
        },
        avatarInsta: {
            image: "http://m.ekeylock.co.kr/file_data/ekeylock/2019/04/16/6c611c78ff31568107bd28882b5ec771.png",
            click: this.onClickInsta,
        },
    }},
    methods: {
        onClickFacebook() {
            console.log("click onClickFacebook");
        },
        onClickBlog() {
            console.log("click onClickBlog");
        },
        onClickInsta() {
            console.log("click onClickInsta");
        },
    },
    watch: {
        
    },
}
</script>

<style scoped>
#avatar-container > * {
    margin: 3px;
}
#des {
    font-size: small;
    color: grey;
}
</style>
