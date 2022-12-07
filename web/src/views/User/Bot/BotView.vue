<template>
<!--///////////////////////////////////-->
<div class="modal fade" id="add-bot-btn" tabindex="-1" style="color:whitesmoke">
        <div class="modal-dialog modal-xl">
            <div class="modal-content" style="background-color:#111d27">
                <div class="modal-header">
                    <h1 class="modal-title fs-5">Create bot</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="add-bot-title" class="form-label">Title</label>
                        <input v-model='botadd.title' type="text" class="form-control" id="add-bot-title">
                    </div>
                    <div class="mb-3">
                        <label for="add-bot-description" class="form-label">Description</label>
                        <textarea v-model="botadd.description" class="form-control" id="add-bot-description" rows="3"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="add-bot-code" class="form-label">Code</label>
                        <textarea v-model="botadd.content" class="form-control" id="add-bot-code" rows="7"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="error_message">{{botadd.error_message}}</div>
                    <button type="button" class="btn btn-primary" @click="add_bot">Save changes</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
<!--///////////////////////////////////-->
    
<!--///////////////////////////////////-->
    <div class = "container">
        <div class = "row">
            <div class = "col-3">
                <div class = "card" style="background-color: #111d27; margin-top: 20px;">
                    <div class = 'card-body'>
                        <img :src="$store.state.user.photo" alt="" style = "width: 100%;">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="background-color: #111d27; margin-top: 20px;">
                    <div class="card-header" style="color:whitesmoke; font-size:120%">
                        My bot
                        <button type="button"
                        class="btn btn-primary float-end"
                        style="font-size:50%"
                        data-bs-toggle="modal"
                        data-bs-target="#add-bot-btn">
                            Created bot
                        </button>
                    </div>
                    <div class="card-body" style="background-color:#212529">
                        <table class="table table-dark table-hover">
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{bot.title}}</td>
                                    <td>{{bot.description}}</td>
                                    <td>
                                        <button type="button" class="btn btn-primary float-end" style="font-size:50%;" @click="remove_bot(bot)">
                                            Delete bot
                                        </button>
                                        <button
                                        type="button"
                                        class="btn btn-primary float-end"
                                        style="font-size:50%; margin-right: 20px;"
                                        data-bs-toggle="modal"
                                        :data-bs-target="'#update-bot-'+ bot.id">
                                            Update bot
                                        </button>
                                    </td>

                                    <div class="modal fade" :id="'update-bot-' + bot.id" tabindex="-1" style="color:whitesmoke">
                                        <div class="modal-dialog modal-xl">
                                            <div class="modal-content" style="background-color:#111d27">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5">Update bot</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="mb-3">
                                                        <label for="add-bot-title" class="form-label">Title</label>
                                                        <input v-model='bot.title' type="text" class="form-control" id="add-bot-title">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-description" class="form-label">Description</label>
                                                        <textarea v-model="bot.description" class="form-control" id="add-bot-description" rows="3"></textarea>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-code" class="form-label">Code</label>
                                                        <textarea v-model="bot.content" class="form-control" id="add-bot-code" rows="7"></textarea>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <div class="error_message">{{bot.error_message}}</div>
                                                    <button type="button" class="btn btn-primary" @click="update_bot(bot)">Save changes</button>
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import { useStore } from 'vuex'
import { ref, reactive } from 'vue';
import $ from 'jquery'
import { Modal } from 'bootstrap/dist/js/bootstrap';

export default{
    setup(){
        const store = useStore();
        let bots = ref([]);

        const botadd = reactive({
            title: '',
            description: '',
            content: '',
            error_message: '',
        });

        const refresh_bot = ()=>{
            $.ajax({
                url: 'http://127.0.0.1:8090/user/bot/query/',
                type: 'get',
                headers:{
                    Authorization: 'Bearer ' + store.state.user.token,
                },
                success(resp){
                    bots.value = resp;
                }
            })
        }
        refresh_bot();

        const add_bot = () =>{
            botadd.error_message = '';
            $.ajax({
                url:'http://127.0.0.1:8090/user/bot/add/',
                type:'post',
                data:{
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    if(resp.error_message === "Success"){
                        botadd.title = '';
                        botadd.description = '';
                        botadd.content = '';
                        Modal.getInstance("#add-bot-btn").hide();
                        refresh_bot();
                    } else{
                        botadd.error_message = resp.error_message;
                    }
                }
            })
        }

        const update_bot = (bot) =>{
            bot.error_message = '';
            $.ajax({
                url:'http://127.0.0.1:8090/user/bot/update/',
                type:'post',
                data:{
                    id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content,
                },
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    if(resp.error_message === "Success"){
                        Modal.getInstance('#update-bot-' + bot.id).hide();
                        refresh_bot();
                    } else{
                        bot.error_message = resp.error_message;
                    }
                }
            })
        }

        const remove_bot = (bot) =>{
            $.ajax({
                url:'http://127.0.0.1:8090/user/bot/remove/',
                type:'post',
                data:{
                    id: bot.id,
                },
                headers:{
                    Authorization: 'Bearer ' + store.state.user.token,
                },
                success(resp){
                    if(resp.error_message === 'Success'){
                        refresh_bot();
                    }
                }
            })
        }

        return{
            bots,
            botadd,
            add_bot,
            update_bot,
            remove_bot,
        }
    }
}
</script>

<style scoped>
    th{
        font-size: 120%;
    }
</style>