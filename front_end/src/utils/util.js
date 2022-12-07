function transPrice(price) {
    return price.toLocaleString('ko-KR');
}

function makeFrame(unit, items, nullItem) {
    const length = items.length;
    const total = Math.ceil(length / unit) * unit;

    const newOne = items.slice();
    const result = new Array(total - length).fill(nullItem);

    return newOne.concat(result);
}

function range(num) {
    return [...Array(Number(num)).keys()];
}

function getPages(row, col, items) {
    const unit = row * col;
    const length = items.length;
    return Math.ceil(length / unit);
}

function getIdx(page, row, col, rows, cols) {
    const unit = rows * cols;
    
    return page * unit + row * cols + col;
}

function beAnonymous(name) {
    return name;
}

function str2date(date) {
    return date;
}

function str2Avatar(str) {
    return str.slice(0, 3).toUpperCase();
}

export default {
    "range": range,
    "makeFrame": makeFrame,
    "getPages": getPages,
    "getIdx": getIdx,
    "transPrice": transPrice,
    "beAnonymous": beAnonymous,
    "str2date": str2date,
    "str2Avatar":str2Avatar,
}