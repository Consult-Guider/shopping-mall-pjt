export class ReviewSelected {
    constructor(rid, rate, content) {
        this.rid = rid;
        this.rate = rate;
        this.content = content;
    }
    
    static of(rid, rate, content) {
        return new ReviewSelected(rid, rate, content);
    }

    static empty() {
        return ReviewSelected.of(null, 3, "").json();
    }

    json() {
        return {
            rid: this.rid,
            rate: this.rate,
            content: this.content,
        };
    }
}
