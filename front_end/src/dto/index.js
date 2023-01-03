import roleType from "@/utils/roleType";

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

export class SignUpReq {
    constructor(role, params) {
        this.role = role;
        this.params = params;
    }

    static of(role, params) {
        return new this(role, params);
    }

    data(attr) {
        return this.params[attr].value;
    }

    json() {
        switch (this.role) {
            case roleType.roles.USER:
                return {
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phoneNum: this.data("phone"),
                };
            case roleType.roles.SELLER:
                    return {
                        email: this.data("email"),
                        password: this.data("password"),
                        name: this.data("name"),
                        phone: this.data("phone"),
                        companyName: this.data("companyName"),
                    };
            case roleType.roles.ADMIN:
                return {
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phone: this.data("phone"),
                };
        
            default:
                throw new Error(`roleType의 형태가 옳바르지 못함. ${this.role}`);
        }
    }
}
