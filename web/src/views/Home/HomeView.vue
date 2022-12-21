<template>
    <PlayGround v-if = "$store.state.home.status === 'playing'"></PlayGround>
    <MatchGround v-if = "$store.state.home.status === 'matching'"></MatchGround>
    <ResultBoard v-if = "$store.state.home.loser !== 'none'"></ResultBoard>
</template>

<script>
import PlayGround from "../../components/PlayGround.vue"
import MatchGround from "../../components/MatchGroud.vue"
import ResultBoard from "../../components/ResultBoard.vue"
import { onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";

export default{
    components: {
        PlayGround,
        MatchGround,
        ResultBoard,
    },
    setup(){
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:8090/websocket/${store.state.user.token}/`;

        let socket = null;
        onMounted(() =>{
            store.commit("updateOpponent", {
                username: "opponent",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })
            socket = new WebSocket(socketUrl);

            socket.onopen = () =>{
                console.log("Connected");
                store.commit("updateSocket", socket);
            }

            socket.onmessage = msg =>{
                const data = JSON.parse(msg.data);
                if(data.event === "start-matching"){
                    store.commit("updateOpponent",{
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    })
                    setTimeout(() =>{
                        store.commit("updateStatus", "playing");
                    }, 200)
                    store.commit('updateGame', data.game);
                } else if(data.event === "move"){
                    console.log(data);
                    const game = store.state.home.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direct(data.a_direction);
                    snake1.set_direct(data.b_direction);
                } else if(data.event === "result"){
                    console.log(data);
                    const game = store.state.home.gameObject;
                    const [snake0, snake1] = game.snakes;
                    if(data.loser === "all"){
                        snake0.status = snake1.status = "died";
                    }
                    else if(data.loser === "A"){
                        snake0.status = "died";
                    }
                    else if(data.loser === "B"){
                        snake1.status = "died";
                    }
                    store.commit("updateLoser", data.loser);
                }
            }

            socket.onclose = () =>{
                console.log("Disconnected");
                store.commit("updateStatus", "matching");
            }
        });

        onUnmounted(() =>{
            socket.close();
        })
    }
}

</script>

<style scoped>
</style>