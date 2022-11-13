import endPoint from "@/cfg/endPoint";
import defaults from "@/cfg/defaults";
import util from "@/utils/util";

export default {
    install(Vue) {
        Vue.config.globalProperties.$ServerUrl = 'http://localhost:8080';

        Vue.config.globalProperties.$endPoint = endPoint;

        Vue.config.globalProperties.$defaults = defaults;

        Vue.config.globalProperties.$util = util;
    }
}