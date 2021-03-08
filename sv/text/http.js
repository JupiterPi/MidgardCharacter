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

function fget(url, response, handler) {
    handler(response);
}

function aget(url, r, handler) {
    async("GET", url, null, function(response) {
        handler(JSON.parse(response));
    });
}

function get(url, handler) {
    async("GET", url, null, function(response) {
        handler(JSON.parse(response));
    });
}

function sget(url) {
    return JSON.parse(sync("GET", url, null));
}

function post(url, body) {
    async("POST", url, body, function() {});
}