export default class {
    static roles = {
        NULL: "NULL",
        USER: "USER",
        SELLER: "SELLER",
        ADMIN: "ADMIN",
    }

    static find(role) {
        const ROLE = role.toUpperCase().replace("ROLE_", "");
        if (Object.keys(this.roles).includes(ROLE)) {
            return ROLE;
        } else {
            throw new Error("적절한 Role이 아님. ex) USER, SELLER, ADMIN로 전달해야 함.");
        }
    }
}