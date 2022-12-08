<template>
    <h2>구매 목록</h2>

<v-divider thickness="3" color="black"/>

<v-table theme="grey" density="comfortable">
<thead>
  <tr>
    <th>
        <v-checkbox 
        v-model="selectAll"
        label="전체 선택" 
        hide-details density="compact"
        />
    </th>
    <th>
      이름
    </th>
    <th>
      옵션
    </th>
    <th>
      수량
    </th>
    <th>
      가격
    </th>
  </tr>
</thead>
<tbody>
  <tr
  v-for="(item, index) of model" :key="index"
  >
    <td>
        <v-checkbox 
        v-model="selected"
        :value="index" 
        hide-details density="compact"
        />
    </td>
    <td>{{(item.name || this.$defaults.cell)}}</td>
    <td>{{(item.option || this.$defaults.cell)}}</td>
    <td>{{(item.num || this.$defaults.cell)}}</td>
    <td>{{(item.price || this.$defaults.cell)}}</td>
  </tr>
  <tr>
  </tr>
</tbody>
</v-table>
</template>

<script>
export default {
    props: {
        value: Array,
    },
    data() {return {
        src: this.value,

        selected: this.$util.range(this.value.length),
        selectAll: true,
    }},
    computed: {
        model: {
            get() { return this.src; },
            set(value) {
                this.src = value;
                this.$emit('input', value);
            },
        },
    },
    watch: {
        selectAll(val) {
            if(val) {
                this.selected = this.$util.range(this.model.length);
            }
        },
        selected(val) {
            this.$emit('select', this.selected);
            if(this.model.length != val.length) {
                this.selectAll = false;
            }
        }
    },
    created() {
        this.$emit('select', this.selected);
    },
}
</script>

<style scoped>
    th {
        background-color: rgb(200, 200, 200);
    }

    .v-checkbox {
        width: 5px;
    }
</style>