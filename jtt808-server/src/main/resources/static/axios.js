const $ = function (id) {
    return document.getElementById(id);
};
const axios = {
    /**
     * js封装ajax请求
     * >>使用new XMLHttpRequest 创建请求对象,所以不考虑低端IE浏览器(IE6及以下不支持XMLHttpRequest)
     * >>使用es6语法,如果需要在正式环境使用,则可以用babel转换为es5语法 https://babeljs.cn/docs/setup/#installation
     *  @param option 请求参数模仿jQuery ajax
     *  调用该方法,data参数需要和请求头Content-Type对应
     *  Content-Type                        data                                     描述
     *  application/x-www-form-urlencoded   'name=哈哈&age=12'或{name:'哈哈',age:12}  查询字符串,用&分割
     *  application/json                     name=哈哈&age=12'                        json字符串
     *  multipart/form-data                  new FormData()                           FormData对象,当为FormData类型,不要手动设置Content-Type
     *  注意:请求参数如果包含日期类型.是否能请求成功需要后台接口配合
     */
    request: (option = {}) => {
        // 初始化请求参数
        let config = Object.assign({
            url: '', // string
            method: 'get', // string 'get' 'post' 'delete'
            dataType: 'json', // string 期望的返回数据类型:'json' 'text' 'document' ...
            async: true, //  boolean true:异步请求 false:同步请求 required
            data: null, // any 请求参数,data需要和请求头Content-Type对应
            params: null, // any url参数
            headers: {}, // 请求头
            timeout: 1000, // string 超时时间:0表示不设置超时
            beforeSend: function (xhr) {
            },
            success: function (result, status, xhr) {
            },
            error: function (xhr, status, error) {
            },
            complete: function (xhr, status) {
            }
        }, option);
        // 参数验证
        // config.headers['Origin'] = '*'
        if (config.dataType === 'json') {
            config.headers['Content-Type'] = 'application/json'
        }
        if (!config.url || !config.method || !config.dataType || config.async === undefined) {
            alert('参数有误');
            return;
        }

        // 创建XMLHttpRequest请求对象
        let xhr = new XMLHttpRequest();
        if ('withCredentials' in xhr) {
        } else if (typeof XDomainRequest != 'undefined') {
            xhr = new XDomainRequest();
        } else {
            alert('不支持[XMLHttpRequest、XDomainRequest]请求！')
            return;
        }

        // 请求开始回调函数
        xhr.addEventListener('loadstart', function (e) {
            config.beforeSend(xhr);
        });
        // 请求成功回调函数
        xhr.addEventListener('load', function (error) {
            const status = xhr.status;
            if ((status >= 200 && status < 300) || status === 304) {
                let result;
                if (xhr.responseType === 'text') {
                    result = xhr.responseText;
                } else if (xhr.responseType === 'document') {
                    result = xhr.responseXML;
                } else {
                    result = xhr.response;
                }
                // 注意:状态码200表示请求发送/接受成功,不表示业务处理成功
                config.success(result, status, xhr);
            } else {
                config.error(xhr, status, error);
            }
        });
        // 请求结束
        xhr.addEventListener('loadend', function (error) {
            config.complete(xhr, xhr.status);
        });
        // 请求出错
        xhr.addEventListener('error', function (error) {
            config.error(xhr, xhr.status, error);
        });
        // 请求超时
        xhr.addEventListener('timeout', function (error) {
            config.error(xhr, 408, error);
        });
        // 设置url参数
        if (config.params) {
            config.url += axios.getUrlParam(config.url, config.params);
        }
        // 初始化请求
        xhr.open(config.method, config.url, config.async);
        // 设置期望的返回数据类型
        xhr.responseType = config.dataType;
        // 设置请求头
        Object.keys(config.headers).forEach(key => {
            xhr.setRequestHeader(key, config.headers[key]);
        })
        // 设置超时时间
        if (config.async && config.timeout) {
            xhr.timeout = config.timeout;
        }
        // 发送请求
        xhr.send(axios.getQueryData(config.data));
    },
    // 把参数params转为url查询参数
    getUrlParam: (url, params) => {
        if (!params) {
            return '';
        }
        let query = params instanceof Object ? axios.getQueryString(params) : params;
        return (url.indexOf('?') !== -1) ? query : '?' + query;
    },
    // 获取ajax请求参数
    getQueryData: (data) => {
        if (!data) {
            return null;
        }
        if (typeof data === 'string') {
            return data;
        }
        if (data instanceof FormData) {
            return data;
        }
        return JSON.stringify(data);
    },
    // 把对象转为查询字符串
    getQueryString: (params) => {
        let paramsArr = [];
        if (params instanceof Object) {
            Object.keys(params).forEach(key => {
                let val = params[key];
                if (val instanceof Date) {
                    val = val.toLocaleString().replaceAll('/','-');
                }
                paramsArr.push(encodeURIComponent(key) + '=' + encodeURIComponent(val));
            });
        }
        return paramsArr.join('&');
    },
    getQueryVariable: (key) => {
        const query = window.location.search.substring(1);
        const paramsArr = query.split("&");
        for (let i = 0; i < paramsArr.length; i++) {
            const pair = paramsArr[i].split("=");
            if (pair[0] === key) {
                return pair[1];
            }
        }
        return null;
    }
}