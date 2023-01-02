export class authReq {
    constructor(role, email, password) {
        this.role = role;
        this.email = email;
        this.password = password;
    }

    static of(role, email, password) {
        return new this(role, email, password);
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
        return this.response.response.data;
    }

    get errorCode() {
        return this.getData().errorCode;
    }
}
