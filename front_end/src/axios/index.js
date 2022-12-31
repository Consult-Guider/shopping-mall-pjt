import axios from 'axios';
import { setInterceptors } from '@/utils/interceptors';
import env from '@/cfg/env';

function createAxios() {
	const instance = axios.create();
	instance.defaults.baseURL = env.backendURL;
	return instance;
}

function createAxiosWithAuth() {
    const instance = axios.create();
	instance.defaults.baseURL = env.backendURL;
	return setInterceptors(instance);
}

export const instance = createAxios();
export const instanceWithAuth = createAxiosWithAuth();
