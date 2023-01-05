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

export class PageReq {
    constructor(page, size) {
        this.page = page;
        this.size = size;
    }

    static of(page, size) {
        return new this(page, size);
    }

    sort(obj, asc=true) {
        this.sortObj = obj;
        this.sortAsc = asc;
        return this;
    }

    params() {
        const params = {
            page: this.page,
            size: this.size,
        };

        if(this.sortObj) {
            const direction = this.sortAsc ? 'asc' : 'desc';
            params.sort = `${this.sortObj},${direction}`;
        }

        return params;
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

export class AdImgRes extends PageResponse {
    pages() {
        return [
            {id: 1, startAt: "2015/01/01", endAt: "2015/01/10", name: "맥심 모카 골드", companyName: "Maxim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg"},
            {id: 2, startAt: "2015/01/26", endAt: "2015/01/31", name: "맥심 모카 레드", companyName: "Midim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg"},
            {id: 3, startAt: "2015/01/22", endAt: "2015/01/29", name: "맥심 모카 블루", companyName: "Mimim", path: "https://cdn.pixabay.com/photo/2022/11/15/04/54/automotive-7593064_960_720.jpg"},
        ];
    }
}
