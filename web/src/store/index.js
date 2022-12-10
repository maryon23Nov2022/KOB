import { createStore } from 'vuex'
import ModuleUser from './user'
import ModuleHome from './home'

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    home: ModuleHome,
  }
})
