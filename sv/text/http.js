/* base */

function async(method, url, body, handler) {
    const request = new XMLHttpRequest();
    request.open(method, url, true);
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(body));
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            handler(request.responseText);
        }
    }
}

function sync(method, url, body) {
    const request = new XMLHttpRequest();
    request.open(method, url, false);
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(body));
    return request.responseText;
}

/* variations */

function get(url, handler) {
    async("GET", url, null, handler);
}

function sget(url) {
    return JSON.parse(sync("GET", url, null));
}

function post(url, body) {
    async("POST", url, body, function() {});
}