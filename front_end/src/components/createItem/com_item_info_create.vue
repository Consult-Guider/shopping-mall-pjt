<template>
<v-container>
<v-row>
    <v-col>
        <com_img_create class="fill" @updateImg="onClickMainImage" />
    </v-col>
    <v-col>
        <v-container id="left-sector">
            <v-row><v-col>
                <v-text-field
                variant="outlined" v-model="item.name"
                label="상품 이름 작성"
                ></v-text-field>
            </v-col></v-row>
            <v-divider />
            
            <v-row><v-col>
                <v-text-field
                variant="outlined" v-model="item.price"
                label="상품 가격 작성. 오직 숫자만 기입."
                ></v-text-field>
            </v-col></v-row>
            <v-divider />

            <v-row><v-col>
                <v-combobox
                v-model="option.value"
                :items="option.list"
                :label="option.label"
                @keydown.enter="onEnterOption"
                chips clearable multiple variant="outlined"
                ></v-combobox>

                <v-btn @click="onClickOptionDelete"
                :disabled="isNotExistedInList"
                >해당 옵션 삭제</v-btn>
            </v-col></v-row>
            <v-divider />

            <v-row><v-col class="gap">
                <input type="number" id="theNumber" min="1" :max="incredment.max" v-model="incredment.value" />
                <v-btn
                    variant="outlined" color="primary"
                    @click="bucket.click" disabled
                >{{bucket.label}}</v-btn>
                <v-btn
                    variant="flat" color="primary"
                    @click="purchase.click" disabled
                >{{purchase.label}}</v-btn>
                <v-btn
                    variant="flat" color="primary"
                    @click="update.click"
                >{{update.label}}</v-btn>
            </v-col></v-row>
        </v-container>
    </v-col>
</v-row>
</v-container>
</template>

<script>
export default {
props: {
    imgList: Array,
    tagList: Array,
},
data() {return {
    item: {
        name: "", 
        price: 0,
        image: null,
    },
    option: {
        label: "옵션 생성. 입력 후 엔터.",
        value: null,
        list: [],
        index_target: -1,
    },
    incredment: {
        max: 100,
        value: 1,
    },
    bucket: {
        label: "장바구니 담기",
        click: null,
    },
    purchase: {
        label: "바로 구매",
        click: null,
    },
    update: {
        label: "상품 등록",
        click: this.onClickUpdate,
    },
}},
methods: {
    onClickUpdate() {
        // 상품 등록시, axios를 이용해서 쿼리날리기.
        const formdata = new FormData();
        const addFormData = (key, value) => {
                if(value) {
                    formdata.append(key, value);
                }
            };
        addFormData("name", this.item.name);
        addFormData("price", this.item.price);
        addFormData("image", this.item.image);
        addFormData("optionList", this.option.list);
        for(const img of this.imgList) {
            addFormData("descriptionList", img);
        }
        addFormData("tagList", this.tagList);

        this.$auth.post(`/item`, formdata, {
            headers: {'Context-Type': 'multipart/form-data'},
        });
    },

    onEnterOption() {
        let pushable = (this.optionValue != null) && (-1 == this.option.list.indexOf(this.optionValue))
        if(pushable) {
            this.option.list.push(this.optionValue);
        }
        this.option.value = null;
    },
    onClickOptionDelete() {
        for(const option of this.optionValueAll) {
            let target = this.option.list.indexOf(option);
            if(-1 < target) {
                this.option.list.splice(target, 1);
            }
        }
        this.option.value = null;
    },
    onClickMainImage(oldone, val) {
        this.item.image = val;
    },
},
computed: {
    optionValue() { 
        return this.option.value ? this.option.value[0] : null; 
    },
    optionValueAll() { 
        return this.option.value; 
    },
    isNotExistedInList() { return (-1 == this.option.index_target); }
},
watch: {
    optionValue(val) { 
        let index_target = this.option.list.indexOf(val);
        this.option.index_target = index_target;
    },
},
}
</script>

<style scoped>
#left-sector > .v-divider {
    margin-top: 20px;
    margin-bottom: 20px;
}
#theNumber {
    text-align: center;

    border: 1px solid grey;
    border-radius: 3px;
}
.gap > * {
    margin: 2px;
    height: 35px;
}
</style>
