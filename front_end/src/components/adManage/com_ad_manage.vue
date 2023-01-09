<template>
    <v-container>
        <v-row>
            <v-col>
                <h1>광고 배너 대시보드</h1>
            </v-col>
        </v-row>

        <v-divider class="mb-3" color="black" thickness="5" />

        <v-row>
            <v-col>
                <v-card class="pa-2" elevation="10">
                <!-- 광고 스케듈러 -->
                <v-timeline 
                class="size-limit"
                truncate-line="both" side="end" align="start" 
                >
                    <v-timeline-item
                    v-for="item of sortByDate(data)" :key="item"
                    :dot-color="colorMarker(item.isStarted)" size="small" 
                        >
                        <v-card class="pa-1" 
                            :color="colorOfItem(item.id)"
                            @click="onClickItem(item)"
                        >
                            <com_unit_timeline v-bind="item" />
                        </v-card>
                    </v-timeline-item>
                </v-timeline>
                </v-card>
            </v-col>
            <v-col>
                <v-card class="pa-2" elevation="10">
                <!-- 광고 추가 수정 -->
                <div class="t2b ygap">
                    <com_img_create :img="input.img.value" @updateImg="handleUpdateImage" @deleteImg="handleDeleteImage" />

                    <v-spacer class="my-4" />

                    <v-text-field 
                    :label="input.itemName.label"
                    variant="outlined" v-model="input.itemName.value"
                    />
                    <v-text-field
                    :label="input.companyName.label"
                    variant="outlined" v-model="input.companyName.value"
                    />
                    <v-text-field
                    :label="input.link.label"
                    variant="outlined" v-model="input.link.value"
                    />
                    <com_select_date 
                    :title="input.startAt.label" 
                    :data="input.startAt.value" @update="x => {input.startAt.value=x}"
                    />
                    <com_select_date 
                    :title="input.endAt.label" 
                    :data="input.endAt.value" @update="x => {input.endAt.value=x}"
                    />

                    <v-btn @click="btn.update.click" :color="btn.update.color"
                    >{{ btn.update.label }}</v-btn>
                    <v-btn @click="btn.delete.click" :color="btn.delete.color"
                    >{{ btn.delete.label }}</v-btn>
                </div>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { ErrRes, AdImgReq } from '@/dto';

export default {
    props: {
        data: Array,
    },
    data() {
        return {
            input: {
                id: {value: null,},
                img: {value: null, multipart: null},
                itemName: {value: null, label: "광고 상품명"},
                companyName: {value: null, label: "회사명"},
                link: {value: null, label: "링크 URL"},
                startAt: {value: null, label: "광고 시작일"},
                endAt: {value: null, label: "광고 만료일"},
            },
            btn: {
                update: {value: null, label: "생성하기", click: this.onClickUpdate, color: "green"},
                delete: {value: null, label: "삭제하기", click: this.onClickDelete, color: "red"},
            },
        }
    },
    computed: {
        IdSelected: {
            get() { 
                return this.input.id.value; 
            },
            set(value) {
                this.input.id.value = value;
            },
        },
    },
    watch: {
        IdSelected(val) {
            this.btn.update.label = val ? "수정하기" : "생성하기";
            this.btn.update.color = val ? "blue" : "green";
        },
    },
    methods: {
        colorMarker(isStarted) {
            return isStarted ? "teal-lighten-3" : "pink";
        },
        sortByDate(data) {
            const obj = [];
            for(const item of data) {
                obj.push(Object.assign({}, item, {
                    isStarted: true,
                    date: item.startAt,
                }));
                obj.push(Object.assign({}, item, {
                    isStarted: false,
                    date: item.endAt,
                }));
            }
            obj.sort((a,b) => {
                return Date.parse(a.date) - Date.parse(b.date);
            });
            return obj;
        },

        onClickUpdate() {
            const id = this.IdSelected;
            const formData = new FormData();
            formData.append(AdImgReq.params.img, this.input.img.multipart);
            formData.append(AdImgReq.params.itemName, this.input.itemName.value);
            formData.append(AdImgReq.params.companyName, this.input.companyName.value);
            formData.append(AdImgReq.params.link, this.input.link.value);
            formData.append(AdImgReq.params.startAt, this.input.startAt.value);
            formData.append(AdImgReq.params.endAt, this.input.endAt.value);

            // 아이디가 존재하면 post 방식으로, 아이디가 존재하지 않으면 put 방식으로.
            const endPoint = id ? `/adimg/${id}` : `/adimg`
            const promise = this.$auth.post(endPoint, formData, {headers: {'Context-Type': 'multipart/form-data'}})

            promise.then(() => {
                // 데이터 새로고침.
                this.$emit('fetchAdData');
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
        onClickDelete() {
            const id = this.IdSelected;

            this.$auth.delete(`/adimg/${id}`)
            .then(() => {
                // 데이터 새로고침.
                this.$emit('fetchAdData');
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
        onClickItem(item) {
            if(this.IdSelected == item.id) {
                this.resetItem();
            } else {
                this.input.id.value = item.id;
                this.input.img.value = item.path;
                this.input.itemName.value = item.name;
                this.input.companyName.value = item.companyName;
                this.input.link.value = item.link;
                this.input.startAt.value = item.startAt;
                this.input.endAt.value = item.endAt;
            }
        },

        colorOfItem(id) { 
            return this.IdSelected == id ? "grey" : "white";
        },
        resetItem() {
            for(const attr in this.input) {
                this.input[attr].value = null;
            }
        },

        handleUpdateImage(_, multipart) {
            this.input.img.multipart = multipart;
        },
        handleDeleteImage() {
            this.input.img.multipart = null;
        },
    },
}
</script>

<style scoped>
    .size-limit {
        height: 80vh;
        overflow-x: auto;
        overflow-y: auto;
    }
    .ygap > * {
        margin-bottom: 4px;
    }
</style>