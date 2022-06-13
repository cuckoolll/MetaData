import request from "./http";

/**
 * 封装post请求
 * @param url
 * @param data
 * @returns {Promise}
 */
export function post(url, data = {}) {
    return new Promise((resolve, reject) => {
        request({ headers: {'Content-Type': 'application/json'}, method: 'post', url: url, data: data})
            .then((response) => {
                resolve(response.data);
            })
            .catch((err) => {
                reject(err);
            });
    });
}

export function get(url) {
    return new Promise((resolve, reject) => {
        request({method: 'get', url: url})
            .then((response) => {
                resolve(response.data);
            })
            .catch((err) => {
                reject(err);
            });
    });
}


