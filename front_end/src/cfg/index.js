import env from "@/cfg/env";
import endPoint from "@/cfg/endPoint";
import defaults from "@/cfg/defaults";
import util from "@/utils/util";

export default {
    install(Vue) {
        Vue.config.globalProperties.$env = env;

        Vue.config.globalProperties.$endPoint = endPoint;

        Vue.config.globalProperties.$defaults = defaults;

        Vue.config.globalProperties.$util = util;
    }
}