import com_header from '@/components/com_header.vue'
import com_footer from '@/components/com_footer.vue'

const components = {
    "com_header": com_header,
    "com_footer": com_footer,
};

export default {
    install(Vue) {
        for(const com in components) {
            Vue.component(`${com}`, components[com]);
        }
    }
};
