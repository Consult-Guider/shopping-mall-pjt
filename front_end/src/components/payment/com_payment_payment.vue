<template>
    <h2>결제 정보</h2>

<v-divider thickness="3" color="black"/>

<v-table theme="grey" density="comfortable">
<thead>
  <tr>
    <th class="text-right">
      총 상품가격
    </th>
    <th class="text-right">
      총 결제금액
    </th>
    <th class="text-right">
      결제 방법
    </th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>{{$util.transPrice((totalPrice ?? this.$defaults.cell))}}원</td>
    <td>{{$util.transPrice((getFinalPrice(totalPrice) ?? this.$defaults.cell))}}원</td>
    <td>카드</td>
  </tr>
  <tr>
  </tr>
</tbody>
</v-table>
</template>

<script>
export default {
    props: {
        selected: Array,
    },
    data() {return {
        
    }},
    methods: {
        getFinalPrice(x) {
            return x;
        },
    },
    computed: {
        totalPrice() {
            let total = 0;
            for(const item of this.selected) {
                total += item.price;
            }
            return total;
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