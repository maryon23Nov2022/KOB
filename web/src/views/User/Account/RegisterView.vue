<template>
    <div style = "font-size: 120%; color: whitesmoke">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">username</label>
                        <input v-model="username" type="text" class="form-control" id="username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">password</label>
                        <input v-model="password" type="password" class="form-control" id="password">
                    </div>
                    <div class="mb-3">
                        <label for="confirmation" class="form-label">password confirmation</label>
                        <input v-model="confirmation" type="password" class="form-control" id="confirmation">
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

import { ref } from 'vue';
import router from '../../../router/index'
import $ from 'jquery'

export default{
    setup(){
        let username = ref('');
        let password = ref('');
        let confirmation = ref('');
        let error_message = ref('');

        const register = () =>{
            $.ajax({
                url: 'http://127.0.0.1:8090/user/account/register/',
                type: 'post',
                data:{
                    username: username.value,
                    password: password.value,
                    confirmedpassword: confirmation.value,
                },
                success(resp){
                    if(resp.error_message === 'Registration completed'){
                        router.push({name: 'user_account_login'});
                    } else{
                        error_message.value = resp.error_message;
                    }
                },
            })
        }

        return{
            username,
            password,
            confirmation,
            error_message,
            register,
        }
    }
}

</script>

<style>
</style>