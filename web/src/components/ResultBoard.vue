<template>
    <div class = "result-board">
        <div class = "result-board-text" v-if = "$store.state.home.loser === 'all'">
            Draw
        </div>
        <div class = "result-board-text" v-else-if = "$store.state.home.loser === 'A' && $store.state.home.a_id == $store.state.user.id">
            Lose
        </div>
        <div class = "result-board-text" v-else-if = "$store.state.home.loser === 'B' && $store.state.home.b_id == $store.state.user.id">
            Lose
        </div>
        <div class = "result-board-text" v-else>
            Win
        </div>
        <div class = "result-board-btn">
            <button @click = "restart" type = "button" class = "btn btn-warning bin-lg">
                Again
            </button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';

export default{
    setup(){
        const store = useStore();
        const restart = ()=>{
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                username: "opponent",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })
        }
        return {
            restart
        }
    }
}

</script>

<style scoped>
    div.result-board{
        height: 50vh;
        width: 30vw;
        background-color: rgba(50, 50, 50, 0.5);
        position: absolute;
        top: 40vh;
        left: 50%;
        transform: translateX(-50%);
    }

    div.result-board-text{
        text-align: center;
        color: whitesmoke;
        font-size: 30px;
        font-style: italic;
        padding-top: 5vh;
    }
    div.result-board-btn{
        text-align: center;
        padding-top: 17vh;
    }
</style>