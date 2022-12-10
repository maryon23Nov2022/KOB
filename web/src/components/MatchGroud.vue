<template>
    <div class = "matchground">
        <div class="row">
            <div class="col-6">
                <div class = "user-photo">
                    <img :src = "$store.state.user.photo" alt = "">
                </div>
                <div class = "user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-6">
                <div class = "user-photo">
                    <img :src = "$store.state.home.opponent_photo" alt = "">
                </div>
                <div class = "user-username">
                    {{ $store.state.home.opponent_username }}
                </div>
            </div>
            <div class = "col-12" style = "text-align: center; margin-top: 4vh;">
                <button @click = "click_match_btn" type="button" class="btn btn-warning btn-lg">{{ match_btn_info }}</button>
            </div>
        </div>
    </div>
</template>


<script>
import { ref } from 'vue';
import { useStore } from 'vuex';

export default{
    setup(){
        const store = useStore();
        let match_btn_info = ref("Play");

        const click_match_btn = () =>{
            if(match_btn_info.value === "Play"){
                match_btn_info.value = "Cancle";
                store.state.home.socket.send(JSON.stringify({
                    event: "start-matching",
                }));
            } else{
                match_btn_info.value = "Play";
                store.state.home.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        return{
            match_btn_info,
            click_match_btn
        }
    }
}
</script>

<style scoped>
    div.matchground{
        width: 60vw;
        height: 70vh;
        margin: 40px auto;
        /* background-color: aqua; */
    }
    div.user-photo{
        text-align: center;
        padding-top: 15vh;
        padding-bottom: 3vh;
    }
    div.user-photo > img{
        border-radius: 50%;
        width: 25vh;
    }
    div.user-username{
        text-align: center;
        font-size: 140%;
        color: whitesmoke;
    }
</style>