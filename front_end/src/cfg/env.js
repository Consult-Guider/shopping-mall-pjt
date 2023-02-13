function transState(origin, state, key) {
    const mapper = {};
    for(const item in origin) {
        const val = origin[item];
        mapper[val.value] = val[key];
    }
    return mapper[state] ?? "상태 이상";
}

export default {
    backendURL: 'http://localhost:8080/api/v1',

    queryMethods: {
        name: {
            label: "제목 기준", 
            value: "name", 
            api: "/item",
            btnSet: {
                init_btn: "date-desc",
                btns: {
                    "date-desc": {label: "최신순", json: {trg: "createdAt", isAsc: false,},},
                    "price-asc": {label: "낮은 가격순", json: {trg: "price", isAsc: true,},},
                    "price-desc": {label: "높은 가격순", json: {trg: "price", isAsc: false,},},
                },
            },
        },
        review: {
            label: "리뷰 기준", 
            value: "review", 
            api: "/review",
            btnSet: {
                init_btn: "date-desc",
                btns: {
                    "date-desc": {label: "최신순", json: {trg: "createdAt", isAsc: false,},},
                },
            },
        },
        qna: {
            label: "QnA 기준", 
            value: "qna", 
            api: "/question",
            btnSet: {
                init_btn: "date-desc",
                btns: {
                    "date-desc": {label: "최신순", json: {trg: "createdAt", isAsc: false,},},
                },
            },
        },
    },

    com_orders: {
        PurchaseReady: {
            name: "장바구니 목록",
            value: "READY",
            api: '/payment/READY',
        },
        PurchaseDone: {
            name: "구매 목록",
            value: "DONE",
            api: '/payment/DONE',
        },
        Cancellation: {
            name: "취소 목록",
            value: "CANCEL",
            api: '/payment/CANCEL',
        },
    },
    util_com_orders: (origin, state, key) => transState(origin, state, key),

    com_reivew: {
        numReviewOfOnePage: 3,
    },

    view_history_reivew: {
        numReviewOfOnePage: 5,
    },
}