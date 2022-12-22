<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <!-- <a class="navbar-brand" href="#">Home</a> -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/record/">Record</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/ranklist/">Ranklist</a>
                </li>
            </ul>

            <ul class="navbar-nav" v-if="$store.state.user.is_login">
                <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    {{$store.state.user.username}}
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="/user/bot/">Bot</a></li>
                    <!-- <li><a class="dropdown-item" href="/user/account/login/">Login</a></li> -->
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" @click="logout">Exit</a></li>
                </ul>
                </li>
            </ul>

            <ul class="navbar-nav" v-else>
                <li class="nav-item">
                    <a class="nav-link" href="/user/account/login/" role="button">
                        Login
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/account/register/" role="button">
                        Register
                    </a>
                </li>
            </ul>

        </div>
    </div>
    </nav>
</template>


<script>

import { useStore } from 'vuex';

export default{

    setup(){
        const store = useStore();
        const logout = () => {
            store.dispatch("logout");
        }
        return{
            logout
        }
    }
}

</script>


<style scoped>
</style>
