<template>
    <div ref = "parent" class = "scoreboard">
        <canvas ref = "canvas" tabindex = "0"></canvas>
    </div>
</template>

<script>
    import {gamemap} from "../assets/Scripts/GameMap"
    import {ref, onMounted} from "vue"

    export default{
        setup() {
            let parent = ref(null);
            let canvas = ref(null);

            onMounted(() => {
                new gamemap(canvas.value.getContext('2d'), parent.value)
            });

            return{
                parent, canvas
            }
        }
    }
</script>

<style scoped>
    div.scoreboard{
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>