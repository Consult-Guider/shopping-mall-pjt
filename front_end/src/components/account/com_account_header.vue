<template>
    <v-card
        variant="flat"
    >
        <div class="left2right">
            <v-sheet class="size-limit text-white bg-blue-darken-1">
                <h3 class="text-a-center">MY 쿠팡</h3>
            </v-sheet>
            <v-sheet class="size-limit text-white bg-blue-lighten-1">
                <div class="fill y-gap">
                    <h5 class="text-center">배송 준비중</h5>
                    
                    <h5 class="text-center">{{numReady}}개</h5>
                </div>
            </v-sheet>
            <v-sheet class="size-limit text-white bg-blue-lighten-1">
                <div class="fill y-gap">
                    <h5 class="text-center">배송 중</h5>
                    
                    <h5 class="text-center">{{numIng}}개</h5>
                </div>
            </v-sheet>
            <v-sheet class="size-limit text-white bg-blue-lighten-1">
                <div class="fill y-gap">
                    <h5 class="text-center">배송 완료</h5>
                    
                    <h5 class="text-center">{{numDone}}개</h5>
                </div>
            </v-sheet>
        </div>
    </v-card>
</template>

<script>
export default {
props: {
    numReady: Number,
    numIng: Number,
    numDone: Number,
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
