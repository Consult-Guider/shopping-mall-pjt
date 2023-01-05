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
                    :label="input.termDay.label" 
                    variant="outlined" v-model="input.termDay.value"
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
                termDay: {value: null, label: "광고기한"},
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
        addDate(createdAt, termDay) {
            return createdAt + termDay
        },
        sortByDate(data) {
            const obj = [];
            for(const item of data) {
                obj.push(Object.assign({}, item, {
                    isStarted: true,
                    date: item.createdAt,
                }));
                obj.push(Object.assign({}, item, {
                    isStarted: false,
                    date: this.addDate(item.createdAt, item.termDay),
                }));
            }
            obj.sort((a,b) => {
                return a.date - b.date;
            });
            return obj;
        },

        onClickUpdate() {
            console.log("call onClickUpdate");
        },
        onClickDelete() {
            console.log("call onClickDelete");
        },
        onClickItem(item) {
            if(this.IdSelected == item.id) {
                this.resetItem();
            } else {
                this.input.id.value = item.id;
                this.input.img.value = item.path;
                this.input.itemName.value = item.name;
                this.input.companyName.value = item.companyName;
                this.input.termDay.value = item.termDay;
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
    .card * {
        margin-left: 4px;
        margin-right: 4px;
    }
    .size-limit {
        height: 80vh;
        overflow-x: auto;
        overflow-y: auto;
    }
    .ygap > * {
        margin-bottom: 4px;
    }
</style>