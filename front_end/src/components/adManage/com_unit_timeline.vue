<template>
    <div class="l2r card">
        <div class="t2b">
            <p>{{ $util.str2date(date) }}</p>
        </div>
        <div class="t2b">
            <strong>{{ name }}</strong>
            <p>{{ companyName }}</p>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        id: Number,
        date: String,
        name: String,
        companyName: String,
    },
}
</script>

<style scoped>
    .card * {
        margin-left: 4px;
        margin-right: 4px;
    }
</style>