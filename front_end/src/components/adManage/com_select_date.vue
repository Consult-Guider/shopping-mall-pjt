<template>
    <v-card variant="outlined">
    <div class="title">
        <h5 class="text-grey">{{ title }}</h5>
    </div>
    <v-container>
        <v-row>
            <v-col>
                <v-text-field 
                v-model="input.year.value" 
                density="compact" type="number" variant="outlined" 
                :label="input.year.label" hide-details @change="onChangeValue"
                />
            </v-col>
            <v-col>
                <v-text-field 
                v-model="input.month.value" 
                density="compact" type="number" variant="outlined" 
                :label="input.month.label" hide-details @change="onChangeValue"
                />
            </v-col>
            <v-col>
                <v-text-field 
                v-model="input.day.value" 
                density="compact" type="number" variant="outlined" 
                :label="input.day.label" hide-details @change="onChangeValue"
                />
            </v-col>
        </v-row>
    </v-container>
    </v-card>
</template>

<script>
export default {
    props: {
        title: String,
        data: String,
        delimiter: {
            default: "/",
            type: String,
        },
    },
    data() {
        return {
            input: {
                year: {value: null, label: "년"},
                month: {value: null, label: "월"},
                day: {value: null, label: "일"},
            },
            delimiters: this.delimiter,
        }
    },
    computed: {
        valueEmitted() {
            const year = this.input.year.value;
            const month = this.input.month.value;
            const day = this.input.day.value;
            return `${year}${this.delimiters}${month}${this.delimiters}${day}`;
        },
    },
    watch: {
        data(val) {
            if(val) {
                this.setDate(...this.split(val));
            } else {
                this.setToday();
            }
        },
    },
    methods: {
        setDate(year, month, day) {
            this.input.year.value = year;
            this.input.month.value = month;
            this.input.day.value = day;

            this.onChangeValue();
        },
        onChangeValue() {
            this.$emit("update", this.valueEmitted);
        },
        split(date) {
            return date.split(this.delimiters).map(Number);
        },
        setToday() {
            const today = new Date();
            this.setDate(
                today.getFullYear(), 
                today.getMonth() + 1, 
                today.getDate()
            );
        },
    },
    created() {
        if(this.data) {
            this.setDate(...this.split(this.data));
        } else {
            this.setToday();
        }
    },
}
</script>

<style scoped>
    .title {
        margin-left: 1rem;
        margin-bottom: -0.75rem;
    }
</style>