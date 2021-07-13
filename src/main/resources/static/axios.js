var axios = {
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
    let defaultOption = Object.assign({
        url: '', // string
        type: 'GET', // string 'GET' 'POST' 'DELETE'
        dataType: 'json', // string 期望的返回数据类型:'json' 'text' 'document' ...
        async: true, //  boolean true:异步请求 false:同步请求 required
        data: {}, // any 请求参数,data需要和请求头Content-Type对应
        headers: {}, // 请求头
        timeout: 1000, // string 超时时间:0表示不设置超时
        beforeSend: function(xhr) {},
        success: function(result, status, xhr) {},
        error: function(xhr, status, error) {},
        complete: function(xhr, status) {}
    }, option);
// 参数验证
defaultOption.headers['Origin'] = '*'
if (defaultOption.dataType === 'json') {
    defaultOption.headers['Content-Type'] = 'application/json'
}
if (!defaultOption.url || !defaultOption.type || !defaultOption.dataType || defaultOption.async === undefined) {
    alert('参数有误');
    return;
}

// 创建XMLHttpRequest请求对象
var xhr = new XMLHttpRequest();
if ('withCredentials' in xhr) {
} else if(typeof XDomainRequest != 'undefined'){
    xhr = new XDomainRequest();
} else {
    alert('不支持[XMLHttpRequest、XDomainRequest]请求！')
    return;
}

// 请求开始回调函数
xhr.addEventListener('loadstart', function(e) {
    defaultOption.beforeSend(xhr);
});
// 请求成功回调函数
xhr.addEventListener('load', function(error) {
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
        defaultOption.success(result, status, xhr);
    } else {
        defaultOption.error(xhr, status, error);
    }
});
// 请求结束
xhr.addEventListener('loadend', function(error) {
    defaultOption.complete(xhr, xhr.status);
});
// 请求出错
xhr.addEventListener('error', function(error) {
    defaultOption.error(xhr, xhr.status, error);
});
// 请求超时
xhr.addEventListener('timeout', function(error)  {
    defaultOption.error(xhr, 408, error);
});
let param = false;
let methodType = defaultOption.type.toUpperCase();
// 如果是"简单"请求,则把data参数组装在url上
if (methodType === 'GET' || methodType === 'DELETE') {
    param = true;
    defaultOption.url += axios.getUrlParam(defaultOption.url, defaultOption.data);
}
// 初始化请求
xhr.open(defaultOption.type, defaultOption.url, defaultOption.async);
// 设置期望的返回数据类型
xhr.responseType = defaultOption.dataType;
// 设置请求头
Object.keys(defaultOption.headers).forEach(key => {
    xhr.setRequestHeader(key, defaultOption.headers[key]);
})
// 设置超时时间
if (defaultOption.async && defaultOption.timeout) {
    xhr.timeout = defaultOption.timeout;
}
// 发送请求.如果是简单请求,请求参数应为null.否则,请求参数类型需要和请求头Content-Type对应
xhr.send(param ? null : axios.getQueryData(defaultOption.data));
},
// 把参数data转为url查询参数
getUrlParam: (url, data) => {
    if (!data) {
        return '';
    }
    let paramsStr = data instanceof Object ? axios.getQueryString(data) : data;
    return (url.indexOf('?') !== -1) ? paramsStr : '?' + paramsStr;
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
getQueryString: (data) => {
    let paramsArr = [];
    if (data instanceof Object) {
        Object.keys(data).forEach(key => {
            let val = data[key];
        // todo 参数Date类型需要根据后台api酌情处理
        if (val instanceof Date) {
            // val = dateFormat(val, 'yyyy-MM-dd hh:mm:ss');
        }
        paramsArr.push(encodeURIComponent(key) + '=' + encodeURIComponent(val));
    });
    }
    return paramsArr.join('&');
}
}