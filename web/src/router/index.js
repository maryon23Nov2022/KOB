import { createRouter, createWebHistory } from 'vue-router'
import ErrorView from '../views/Error/ErrorView'
import HomeView from "../views/Home/HomeView"
import RanklistView from "../views/Ranklist/RanklistView"
import BotView from "../views/User/Bot/BotView"
import LoginView from "../views/User/Account/LoginView"
import RegisterView from "../views/User/Account/RegisterView"
import store from "../store/index"

const routes = [
  {
    path: "/error/",
    name: "errorindex",
    component: ErrorView,
    meta:{
      RequestAuth: false
    }
  },
  {
    path: "/",
    name: "homeindex",
    component: HomeView,
    meta:{
      RequestAuth: true
    }
  },
  {
    path: "/ranklist/",
    name: "ranklistindex",
    component: RanklistView,
    meta:{
      RequestAuth: true
    }
  },
  {
    path: "/user/bot/",
    name: "botindex",
    component: BotView,
    meta:{
      RequestAuth: true
    }
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: LoginView,
    meta:{
      RequestAuth: false
    }
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: RegisterView,
    meta:{
      RequestAuth: false
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect:"/error/"
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) =>{
  if(to.meta.RequestAuth && !store.state.user.is_login){
    const jwt_token = localStorage.getItem("jwt_token");
    if(jwt_token){
      store.commit("updateToken", jwt_token);
      store.dispatch("getinfo", {
        success(){
          next();
        }
      })
    } else{
      next({name: "user_account_login"});
    }
  } else{
    next();
  }
})

export default router
