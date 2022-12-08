<template>
    <h2>받는 사람 정보</h2>

    <v-divider thickness="3" color="black"/>

    <v-table theme="grey" density="comfortable">
    <thead>
      <tr>
        <th class="text-right">
          이름
        </th>
        <th class="text-right">
          배송 주소
        </th>
        <th class="text-right">
          휴대폰 번호
        </th>
        <th class="text-right">
          배송 요청 사항
        </th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>{{(model.name || this.$defaults.cell)}}</td>
        <td>{{(model.address || this.$defaults.cell)}}</td>
        <td>{{(model.phoneNum || this.$defaults.cell)}}</td>
        <td>{{(model.toDelivery || this.$defaults.cell)}}</td>
      </tr>
      <tr>
      </tr>
    </tbody>
  </v-table>
</template>

<script>
export default {
    props: {
        value: Object,
    },
    data() {return {
        src: this.value,
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
}
</script>

<style scoped>
    table tr {
        display:flex;
        flex-direction: column;
        float: left;

        width: 200px;
    }

    thead,tbody {
        float: left;
    }

    th {
        background-color: rgb(200, 200, 200);
    }
</style>