import { createStore } from 'vuex';
import roleType from '@/utils/roleType';
import util from '@/utils/util';
import { DelAccessToken, saveAccessToken } from '@/utils/localStorage';

export default createStore({
	state: {
		user: {
			email: null,
			role: roleType.roles.NULL,
		},
	},
	getters: {
		isLogin(state) {
			return state.user.email != null;
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
		login(state, token, isAuto=false) {
			// token Decoding.
			const decoded = util.JWTDecode(token);

			// 스토어에 저장.
			state.user = {
				email: decoded.email,
				role: roleType.find(decoded.role),
			};

			// localStorage에 저장.
			if(isAuto) { saveAccessToken(token); }
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
