<template>
    <v-card
        variant="flat"
    >
        <div class="left2right">
            <v-sheet class="size-limit text-white bg-blue-darken-1">
                <h3 class="text-a-center">MY 쿠팡</h3>
            </v-sheet>
            <v-sheet 
                v-for="(val, key) in statistic" :key="key"
                class="size-limit text-white bg-blue-lighten-1"
            >
                <div class="fill y-gap">
                    <h5 class="text-center">{{ key }}</h5>
                    
                    <h5 class="text-center">{{ val }}개</h5>
                </div>
            </v-sheet>
        </div>
    </v-card>
</template>

<script>
export default {
props: {
    statistic: Object,
},
}
</script>

<style scoped>
.size-limit {
    width: 200px;
    height: 100px;
}
.left2right {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>
