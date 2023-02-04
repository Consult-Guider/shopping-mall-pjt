import { createStore } from 'vuex';
import roleType from '@/utils/roleType';
import util from '@/utils/util';
import { DelAccessToken, loadAccessToken, saveAccessToken } from '@/utils/localStorage';

class Claim {
	constructor(id, email, role) {
		this.id = id;
		this.email = email;
		this.role = role;
	}

	static of(id, email, role) {
		return new Claim(id, email, role);
	}

	json() {
		return {
			id: this.id,
			email: this.email,
			role: roleType.find(this.role),
		};
	}

	static empty() {
		const obj = Claim.of(null, null, "NULL");
		return obj.json()
	}
}

function baseLogin(state, token, isAuto) {
	// token Decoding.
	const decoded = util.JWTDecode(token);

	// 스토어에 저장.
	state.user = Claim.of(decoded.id, decoded.email, decoded.role).json();

	// localStorage에 저장.
	if(isAuto) { saveAccessToken(token); }
}

export default createStore({
	state: {
		user: Claim.empty(),
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
			state.user = Claim.empty();

			// localStorage에서 삭제.
			DelAccessToken();
		},
	},
});
