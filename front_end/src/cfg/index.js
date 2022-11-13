export default {
    install(Vue) {
        Vue.config.globalProperties.$ServerUrl = 'http://localhost:8080';
    }
}