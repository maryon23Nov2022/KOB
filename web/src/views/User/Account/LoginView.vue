<template>
    <div style = "font-size: 120%; color: whitesmoke">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="login">
                    <div class="mb-3">
                    <label for="username" class="form-label">username</label>
                    <input v-model="username" type="text" class="form-control" id="username">
                    </div>
                    <div class="mb-3">
                    <label for="password" class="form-label">password</label>
                    <input v-model="password" type="password" class="form-control" id="password">
                    </div>
                    <div class="error_message">
                        {{error_message}}
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '../../../router/index'

export default{
    setup(){
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        const login = () => {
            error_message.value = "";
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success(){
                    store.dispatch("getinfo", {
                        success(){
                            router.push({name: 'homeindex'});
                        }
                    })
                },
                error(){
                    error_message.value = "failed";
                }
            })
        }
        return {
            username,
            password,
            error_message,
            login,
        }
    }
}
</script>

<style scoped>
    button{
        width: 100%;
    }
</style>