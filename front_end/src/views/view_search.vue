<template>
    <v-container>
        <v-row>
            <v-col>
                <div class="t2b">
                    <h3>{{ `"${keyword}"에 대한 ${findLabel(method)} 검색 결과` }}</h3>

                    <com_sort :btns="btns" :init="init_btn" @update="updateSort"/>

                    <com_items v-bind="items" :data="data" />

                    <v-pagination
                    v-model="pagination.page"
                    :length="pagination.total"
                    ></v-pagination>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { PageReq, ErrRes, ItemSearchedRes, ReviewSearchedRes, QnASearchedRes } from '@/dto';

export default {
data() {return {
    pagination: {
        page: 1,
        total: 1,
    },
    items: {
        cols: 4,
    },
    data: [],

    sortVal: {
        obj: null,
        isAsc: false,
    },

    init_btn: "",
    btns: {},
}},
computed: {
    keyword() {
        return this.$route.query.keyword;
    },
    method() {
        return this.$route.query.method;
    },
},
methods: {
    fetchItemsSearched: async function() {
        const page = PageReq.of(this.pagination.page-1, 16);
        page.sort(this.sortVal.obj, this.sortVal.isAsc);

        const api = this.findApi(this.method);

        this.$http.get(api, {
            params: {
                keyword: this.keyword,
                ...page.params(),
            },
        }).then(res => {
            const response = this.findDto(this.method, res)
            this.data = response.pages();
            this.pagination.total = response.getTotalPages();
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
    updateSort(json) {
        const trg = json.trg;
        const isAsc = json.isAsc;

        this.sortVal.obj = trg;
        this.sortVal.isAsc = isAsc;

        this.fetchItemsSearched();
    },
    findKey(label) {
        const method = this.$env.queryMethods;
        return Object.keys(method).find(key => method[key].value == label);
    },
    findLabel(label) {
        return this.$env.queryMethods[this.findKey(label)].label;
    },
    findApi(label) {
        return this.$env.queryMethods[this.findKey(label)].api;
    },
    setBtnSet(label) {
        const json = this.$env.queryMethods[this.findKey(label)].btnSet;
        
        this.init_btn = json.init_btn;
        this.btns = json.btns;
    },
    findDto(label, res) {
        switch (this.findKey(label)) {
            case "name":
                return ItemSearchedRes.of(res);
            case "review":
                return ReviewSearchedRes.of(res);
            case "qna":
                return QnASearchedRes.of(res);
            default:
                return ItemSearchedRes.of(res);
        }
    }
},
watch: {
    keyword() {
        this.setBtnSet(this.method);
        this.fetchItemsSearched();
    },
    method(val) {
        this.setBtnSet(val);
        this.fetchItemsSearched();
    },
},
created() {
    this.setBtnSet(this.method);
    this.fetchItemsSearched();
},
}
</script>

<style>

</style>
