<template>
    <div style = "font-size: 120%; color: whitesmoke;">
        <table class="table table-dark table-hover">
            <thead>
                <tr>
                    <th>player</th>
                    <th>rating</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for = "user in users" :key = "user.id">
                    <td>
                        <img :src = "user.photo" class = "record-user-photo">
                        &nbsp;
                        <span class = "record-user-username">{{ user.name }}</span>
                    </td>
                    <td>{{ user.rating }}</td>
                </tr>
            </tbody>
        </table>
        <nav aria-label="...">
            <ul class="pagination" style = "float: right">
                <li class="page-item" @click = "click_page(-2)">
                    <a class="page-link" href="#">Previous</a>
                </li>
                <li :class = "'page-item ' + page.is_active" v-for = "page in pages" :key = "page.number" @click = "click_page(page.number)">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>
                <li class="page-item" @click = "click_page(-1)">
                    <a class="page-link" href="#">Next</a>
                </li>
            </ul>
        </nav>
    </div>
</template>

<script>
import { useStore } from 'vuex';
import { ref } from 'vue';
import $ from 'jquery';

export default{
    setup(){
        const store = useStore();
        let users = ref([]);
        let current_page = 1;
        let total_users = 0;
        let pages = ref([]);

        const click_page = page =>{
            if(page === -2) page = current_page - 1;
            else if(page === -1) page = current_page + 1;
            let max_pages = parseInt((total_users + 3) / 4);

            if(page >= 1 && page <= max_pages){
                pull_page(page);
            }
        }

        const update_pages = () =>{
            let max_pages = parseInt((total_users + 3) / 4);
            let new_pages = [];
            for(let i = current_page - 1; i <= current_page + 2; ++ i){
                if(i >= 1 && i <= max_pages){
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const pull_page = page =>{
            current_page = page;
            $.ajax({
                url: 'http://127.0.0.1:8090/ranklist/getlist/',
                data: {
                    page,
                },
                type: 'get',
                headers:{
                    Authorization: 'Bearer ' + store.state.user.token,
                },
                success(resp){
                    users.value = resp.users;
                    total_users = resp.users_count;
                    update_pages();
                    // console.log(resp);
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        pull_page(current_page);
        return{
            users,
            pages,
            click_page,
        }
    }
}
</script>

<style scoped>
img.record-user-photo{
    width: 5vh;
    border-radius: 50%;
}
</style>