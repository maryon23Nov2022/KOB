<template>
    <div class = "matchground">
        <div class="row">
            <div class="col-4">
                <div class = "user-photo">
                    <img :src = "$store.state.user.photo" alt = "">
                </div>
                <div class = "user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class = "user-select-bot">
                    <select v-model = "select_bot" class="form-select" aria-label="Default select example">
                        <option value = "-1" selected>Manually</option>
                        <option v-for = "bot in bots" :key = "bot.id" :value="bot.id">
                            {{bot.title}}
                        </option>
                    </select>
                </div>
            </div>
            <div class="col-4">
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
import $ from 'jquery';

export default{
    setup(){
        const store = useStore();
        let match_btn_info = ref("Play");
        let bots = ref([]);
        let select_bot = ref(-1);

        const click_match_btn = () =>{
            if(match_btn_info.value === "Play"){
                match_btn_info.value = "Cancle";
                console.log(select_bot.value);
                store.state.home.socket.send(JSON.stringify({
                    event: "start-matching",
                    bot_id: select_bot.value,
                }));
            } else{
                match_btn_info.value = "Play";
                store.state.home.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        const refresh_bot = ()=>{
            $.ajax({
                url: 'http://8.130.33.113/api/user/bot/query/',
                type: 'get',
                headers:{
                    Authorization: 'Bearer ' + store.state.user.token,
                },
                success(resp){
                    bots.value = resp;
                }
            })
        }
        refresh_bot();      //Get bot info dynamically from cloud

        return{
            match_btn_info,
            click_match_btn,
            bots,
            select_bot,
        }
    }
}
</script>

<style scoped>
    div.matchground{
        width: 60vw;
        height: 70vh;
        margin: 40px auto;
        /* background-color: gray; */
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
    div.user-select-bot{
        padding-top: 15vh;
    }
    div.user-select-bot.select{
        width: 60%;
        margin: 0 auto;
    }
</style>