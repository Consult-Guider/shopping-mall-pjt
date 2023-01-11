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
                    id: this.data("uid"),
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phoneNum: this.data("phoneNum"),
                    address: this.data("address"),
                };
            case roleType.roles.SELLER:
                return {
                    id: this.data("uid"),
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phoneNum: this.data("phoneNum"),
                    companyName: this.data("companyName"),
                    address: this.data("address"),
                };
            case roleType.roles.ADMIN:
                return {
                    id: this.data("uid"),
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phoneNum: this.data("phoneNum"),
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

export class AdImgReq {
    static params = {
        img: 'path',
        itemName: 'itemName',
        companyName: 'companyName',
        link: 'link',
        startAt: 'startAt',
        endAt: 'endAt',
    };
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
        return this.response.data.data;
    }
}

export class PageResponse extends BaseResponse {
    getData() {
        return this.response.data.data.content;
    }

    getMeta() {
        return this.response.data.data.pageable;
    }
}

export class ErrRes extends BaseResponse {
    getData() {
        return this.response.response.data;
    }

    get errorCode() { return this.getData().errorCode; }
}

export class authRes extends BaseResponse {
    getData() {
        return this.response.data;
    }
    get token() {
        return this.getData().token;
    }
}

export class LoginRes extends BaseResponse {
    get id() { return this.getData().id; }
    get email() { return this.getData().email; }
    get name() { return this.getData().name; }
    get phoneNum() { return this.getData().phoneNum; }
    get companyName() { return this.getData().companyName; }
    get address() { return this.getData().address; }
}

export class AdImgRes extends PageResponse {
    transform(unit) {
        return {
            "id": unit.id,
            "name": unit.itemName,
            "companyName": unit.companyName,
            "path": unit.path,
            "link": unit.link,
            "startAt": unit.startAt,
            "endAt": unit.endAt,
        };
    }

    pages() {
        return this.getData().map(this.transform);
    }
}

export class AdImgRecommendRes extends BaseResponse {
    transform(unit) {
        return {
            "src": unit.path,
            "link": unit.link,
        };
    }

    json() {
        return this.transform(this.getData());
    }
}

export class ItemRes extends BaseResponse {
    transform(unit) {
        return {
            name: unit.name,
            price: unit.price,
            imgPath: unit.imagePath,
            
            options: unit.optionList,
            descriptions: unit.descriptionList,
            reviews: unit.reviewList,
            queries: unit.questionList,
            tags: unit.tagList,
        };
    }

    json() {
        return this.transform(this.getData());
    }
}
