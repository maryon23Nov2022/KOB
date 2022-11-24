<template>
    <div class = "playground">
        <ScoreBoard />
    </div>
</template>


<script>
    import ScoreBoard from './ScoreBoard.vue'

    export default{
        components:{
            ScoreBoard,
        }
    }
</script>

<style scoped>
div.playground{
    width: 60vw;
    height: 70vh;
    background: lightblue;
    margin: 40px auto;
}
</style>