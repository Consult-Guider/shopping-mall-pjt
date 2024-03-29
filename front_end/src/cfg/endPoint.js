export default {
    "notFound": "/items/error_404",

    "login": "/login",
    "join": "/join",

    "home": "/",
    "search": "/search",
    "item": (iid) => `/items/${iid}`,
    "bucket": "/bucket",
    
    "myPage": "/account",

    "historyQuery": "/historyQuery",
    "historyReview": "/historyReview",

    "updateAccount": "/updateAccount",

    "createItem": "/createItem",

    "backend": {
        // 각 역할군에 따른 EndPoint.",
        "USER":   "/user",
        "SELLER": "/seller",
        "ADMIN":  "/admin",
    },
}