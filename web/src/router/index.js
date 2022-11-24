import { createRouter, createWebHistory } from 'vue-router'
import ErrorView from '../views/Error/ErrorView'
import HomeView from "../views/Home/HomeView"
import RanklistView from "../views/Ranklist/RanklistView"
import BotView from "../views/User/Bot/BotView"

const routes = [
  {
    path: "/error/",
    name: "errorindex",
    component: ErrorView,
  },
  {
    path: "/",
    name: "homeindex",
    component: HomeView,
  },
  {
    path: "/ranklist/",
    name: "ranklistindex",
    component: RanklistView,
  },
  {
    path: "/user/bot/",
    name: "botindex",
    component: BotView,
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

export default router
