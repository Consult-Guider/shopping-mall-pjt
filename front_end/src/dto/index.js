import roleType from "@/utils/roleType";

export class authReq {
    constructor(role, email, password) {
        this.role = role;
        this.email = email;
        this.password = password;
    }

    static of(email, password) {
        return new this(roleType.roles.USER, email, password);
    }

    json() {
        return {
            role: this.role,
            email: this.email,
            password: this.password,
        };
    }
}

export class BaseResponse {
    constructor(response) {
        this.response = response;
    }

    static of(response) {
        return new this(response);
    }

    getData() {
        return this.response.data;
    }
}

export class authRes extends BaseResponse {
    get token() {
        return this.getData().token;
    }
}

export class ErrRes {
    constructor(response) {
        this.response = response;
    }

    static of(response) {
        return new this(response);
    }

    getData() {
        return this.response.data;
    }

    get errorCode() {
        return this.getData().errorCode;
    }
}
