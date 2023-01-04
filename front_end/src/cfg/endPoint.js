export default {
    "notFound": "/items/error_404",

    "login": "/login",
    "join": "/join",

    "home": "/",
    "search": "/search",
    "item": (iid) => `/items/${iid}`,
    "bucket": "/bucket",
    
    "myPage": "/account",
    "exchange": "/exchange",

    "historyQuery": "/historyQuery",
    "historyReview": "/historyReview",

    "updateAccount": "/updateAccount",

    "payment": "/payment",

    "createItem": "/createItem",

    "backend": {
        // 각 역할군에 따른 EndPoint.",
        "USER":   "/user",
        "SELLER": "/seller",
        "ADMIN":  "/admin",
    },
}