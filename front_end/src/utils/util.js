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
    const json = str2date2json(date);

    const year = json.year;
    const month = json.month < 10 ? `0${json.month}` : json.month;
    const day = json.day < 10 ? `0${json.day}` : json.day;
    return `${year}-${month}-${day}`;
}

function str2date2json(date) {
    const strDate = Date.parse(date);
    const objDate = new Date(strDate);
    return {
        year: objDate.getFullYear(),
        month: objDate.getMonth() + 1,
        day: objDate.getDate(),

        // 요일
        dayOfWeek: objDate.getDay(),

        hour: objDate.getHours(),
        minute: objDate.getMinutes(),
        second: objDate.getSeconds(),

        milliSecond: objDate.getMilliseconds(),
    };
}

function str2Avatar(str) {
    return str.slice(0, 3).toUpperCase();
}

function multipart2Str(multifile) {
    if(multifile == null) { return null; }
    return URL.createObjectURL(multifile);
}

function JWTDecode(token) {
    // payload만 취득
    const base64Payload = token.split('.')[1]; 
    // Base64 해독
    const payload = atob(base64Payload); 
    // Json Parsing
    return JSON.parse(payload);
}

export default {
    "range": range,
    "makeFrame": makeFrame,
    "getPages": getPages,
    "getIdx": getIdx,
    "transPrice": transPrice,
    "beAnonymous": beAnonymous,
    "str2date": str2date,
    "str2date2json": str2date2json,
    "str2Avatar":str2Avatar,
    "multipart2Str": multipart2Str,
    "JWTDecode": JWTDecode,
}