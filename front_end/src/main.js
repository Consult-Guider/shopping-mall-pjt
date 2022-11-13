import { createApp } from 'vue'
import App from '@/App.vue'
import vuetify from '@/plugins/vuetify'
import { loadFonts } from '@/plugins/webfontloader'

import { instance, instanceWithAuth } from "@/axios";
import store from '@/store'

import router from '@/views';
import compoent from '@/components';
import configuration from '@/cfg';

loadFonts();

const app = createApp(App);

app.config.globalProperties.$http = instance;
app.config.globalProperties.$auth = instanceWithAuth;

app.use(configuration);
app.use(compoent);

app.use(vuetify);
app.use(store);
app.use(router);
app.mount('#app');
