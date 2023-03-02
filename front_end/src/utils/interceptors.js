import { loadAccessToken } from '@/utils/localStorage';

const TOKEN_PROFIX = "Bearer"

// TODO: 아래 삽입한 log들은 개발을 위해서만 필요한 것이므로 배포 전에는 제거하기.
export function setInterceptors(axios) {
    // 요청 시,
	axios.interceptors.request.use(
		(config) => {
            console.log("request 성공");
            console.log(config);
			const token = loadAccessToken();
			
			config.headers.Authorization = token ? `${TOKEN_PROFIX} ${token}` : "";
			return config;
		},
		(error) => {
            console.log("request 실패");
            console.log(error);

			return Promise.reject(error);
		},
	);

    // 응답 시,
	axios.interceptors.response.use(
		(response) => {
            console.log("response 성공");
            console.log(response);

			return response;
		},
		(error) => {
            console.log("response 실패");
            console.log(error);

			return Promise.reject(error);
		},
	);
	return axios;
}