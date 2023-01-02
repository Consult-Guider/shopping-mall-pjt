import { createStore } from 'vuex';
import roleType from '@/utils/roleType';
import util from '@/utils/util';
import { DelAccessToken, loadAccessToken, saveAccessToken } from '@/utils/localStorage';

function baseLogin(state, token, isAuto) {
	// token Decoding.
	const decoded = util.JWTDecode(token);

	// 스토어에 저장.
	state.user = {
		email: decoded.email,
		role: roleType.find(decoded.role),
	};

	// localStorage에 저장.
	if(isAuto) { saveAccessToken(token); }
}

export default createStore({
	state: {
		user: {
			email: null,
			role: roleType.roles.NULL,
		},
	},
	getters: {
		isLogin(state) {
			const token = loadAccessToken();
			if (token && !state.user.email) {
				baseLogin(state, token, false);
			}
			return state.user.email;
		},
		isUser(state) {
			return state.user.role == roleType.roles.USER;
		},
		isSeller(state) {
			return state.user.role == roleType.roles.SELLER;
		},
		isAdmin(state) {
			return state.user.role == roleType.roles.ADMIN;
		},
	},
	mutations: {
		login(state, payload) {
			const token = payload.token;
			const isAuto = payload.isAuto;

			baseLogin(state, token, isAuto);
		},
		logout(state) {
			// 스토어에서 삭제.
			state.user = {
				email: null,
				role: roleType.roles.NULL,
			};

			// localStorage에서 삭제.
			DelAccessToken();
		},
	},
});
