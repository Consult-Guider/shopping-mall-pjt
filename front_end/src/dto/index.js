import roleType from "@/utils/roleType";

// Request

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

export class UserUpdateReq {
    constructor(role, params) {
        this.role = role;
        this.params = params;
    }

    static of(role, params) {
        return new this(role, params);
    }

    data(attr) {
        return this.params[attr];
    }

    json() {
        switch (this.role) {
            case roleType.roles.USER:
                return {
                    uid: this.data("id"),
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phoneNum: this.data("phone"),
                    address: this.data("address"),
                };
            case roleType.roles.SELLER:
                return {
                    uid: this.data("id"),
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phone: this.data("phone"),
                    companyName: this.data("companyName"),
                    address: this.data("address"),
                };
            case roleType.roles.ADMIN:
                return {
                    uid: this.data("id"),
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phone: this.data("phone"),
                    address: this.data("address"),
                };
        
            default:
                throw new Error(`roleType의 형태가 옳바르지 못함. ${this.role}`);
        }
    }
}

// Response

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

export class PageResponse extends BaseResponse {
    getData() {
        return this.response.data.data;
    }
}

export class ErrRes extends BaseResponse {
    getData() {
        return this.response.response.data;
    }

    get errorCode() { return this.getData().errorCode; }
}

export class authRes extends BaseResponse {
    get token() {
        return this.getData().token;
    }
}

export class LoginRes extends PageResponse {
    get id() { return this.getData().id; }
    get email() { return this.getData().email; }
    get name() { return this.getData().name; }
    get phoneNum() { return this.getData().phoneNum; }
    get companyName() { return this.getData().companyName; }
    get address() { return this.getData().address; }
}
