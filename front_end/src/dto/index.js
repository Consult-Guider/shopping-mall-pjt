import roleType from "@/utils/roleType";
import env from "@/cfg/env";

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
                        phoneNum: this.data("phone"),
                        companyName: this.data("companyName"),
                    };
            case roleType.roles.ADMIN:
                return {
                    email: this.data("email"),
                    password: this.data("password"),
                    name: this.data("name"),
                    phoneNum: this.data("phone"),
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

export class ReviewReq {
    constructor(rating, content) {
        this.rating = rating;
        this.content = content;
    }

    static of(rating, content) {
        return new this(rating, content);
    }

    json() {
        return {
            rating: this.rating,
            content: this.content,
        };
    }
}

export class QnAReq {
    constructor(content) {
        this.content = content;
    }

    static of(content) {
        return new this(content);
    }

    json() {
        return {
            content: this.content,
        };
    }
}

export class BucketReq {
    constructor(itemId, count, optionList) {
        this.itemId = itemId;
        this.count = count;
        this.optionList = optionList;
    }

    static of(itemId, count, optionList) {
        return new this(itemId, count, optionList);
    }

    json() {
        return {
            itemId: this.itemId,
            count: this.count,
            optionList: this.optionList,
        };
    }
}

export class BucketAllReq {
    constructor(pids) {
        this.pids = pids;
    }

    static of(pids) {
        return new this(pids);
    }

    json() {
        return {
            itemIds: this.pids,
        };
    }
}

export class CancelReq {
    constructor(paymentIdList) {
        this.paymentIdList = paymentIdList;
    }

    static of(paymentIdList) {
        return new this(paymentIdList);
    }

    json() {
        return {
            itemIds: this.paymentIdList,
        };
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
        return this.response.data.data;
    }

    transform(unit) {
        return unit;
    }

    json() {
        return this.transform(this.getData());
    }
}

export class PageResponse extends BaseResponse {
    getData() {
        return this.response.data.data.content;
    }

    getMeta() {
        return this.response.data.data.pageable;
    }

    getTotalPages() {
        return this.response.data.data.totalPages;
    }

    getTotalElements() {
        return this.response.data.data.totalElements;
    }

    transform(unit) {
        return unit;
    }

    pages() {
        return this.getData().map(this.transform);
    }
}

export class ErrRes extends BaseResponse {
    getData() {
        try {
            return this.response.response.data;
        } catch {
            console.log(this.response)
            return {
                errorCode: null,
            };
        }
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
            iid: unit.id,
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

export class ItemSearchedRes extends PageResponse {
    transform(unit) {
        return {
            "iid": unit.id,
            "src": unit.imagePath,
            "name": unit.name,
            "price": unit.price,
        };
    }

    pages() {
        return this.getData().map(this.transform);
    }
}

export class QnASearchedRes extends PageResponse {
    transform(unit) {
        return {
            "iid": unit.itemId,
            "src": unit.imagePath,
            "name": unit.itemName,
            "price": unit?.price ?? 0,
        };
    }

    pages() {
        return this.getData().map(this.transform);
    }
}

export class ReviewRes extends PageResponse {
    transform(unit) {
        return {
            "rid": unit.id,
            "createdAt": unit.createdAt,

            "rating": unit.rating,
            "content": unit.content,

            "userId": unit.userId,
            "userName": unit.userName,

            "itemId": unit.itemId,
            "itemName": unit.itemName,
            "option": unit.option,

            "likes": [],
            "dislikes": [],
        };
    }

    pages() {
        return this.getData().map(this.transform);
    }
}

export class ReviewStatRes extends BaseResponse {
    transform(unit) {
        return {
            avg: unit.average,
        };
    }

    json() {
        return this.transform(this.getData());
    }
}

export class QnARes extends PageResponse {
    static isQuestion(unit) {
        return unit.userId;
    }

    transform(unit) {
        return {
            "qid": unit.id,
            "createdAt": unit.createdAt,

            "content": unit.content,

            "userId": unit.userId,
            "userName": unit.userName,

            "sellerId": unit.sellerId,
            "sellerName": unit.sellerName,
            "companyName": "",

            "itemId": unit.itemId,
            "itemName": unit.itemName,

            "user": {
                id: unit.userId, 
                name: unit.userName, 
                option: "",
            },

            "kind": QnARes.isQuestion(unit) ? "Q" : "A",
        };
    }

    pages() {
        return this.getData().map(this.transform);
    }
}

export class QnAUnitRes extends BaseResponse {
    static isQuestion(unit) {
        return unit.userId;
    }

    transform(unit) {
        return {
            "qid": unit.id,
            "createdAt": unit.createdAt,

            "content": unit.content,

            "userId": unit.userId,
            "userName": unit.userName,

            "sellerId": unit.sellerId,
            "sellerName": unit.sellerName,
            "companyName": "",

            "itemId": unit.itemId,
            "itemName": unit.itemName,

            "user": {
                id: unit.userId, 
                name: unit.userName, 
                option: "",
            },

            "kind": QnAUnitRes.isQuestion(unit) ? "Q" : "A",
        };
    }

    json() {
        return this.transform(this.getData());
    }
}

export class TagRes extends PageResponse {
    transform(unit) {
        return {
            "name": unit.name,
        };
    }
}

export class PurchaseStatisticRes extends BaseResponse {
    transform(unit) {
        const arr = [
            {name: env.com_orders.PurchaseReady.name, value: unit.countDeliveryByReady},
            {name: env.com_orders.PurchaseDone.name, value: unit.countDeliveryByDONE},
            {name: env.com_orders.Cancellation.name, value: unit.countDeliveryByCancel},
        ]
        let obj = {};
        arr.forEach((element) => {
            obj[element.name] = element.value;
        });
        return obj;
    }
}

export class PurchaseRes extends PageResponse {
    transform(unit) {
        return {
            pid: unit.id,
            iid: unit.itemId, 
            orderedAt: unit.createdAt, 
            state: unit.processType, 
            name: unit.itemName, 
            price: unit.itemPrice, 
            num: unit.count, 
            src: unit.itemImagePath,
            optionList: unit.optionList,
        };
    }
}
