
// http://210.76.74.2/yjzf/api/v2/sys/sysUtil/dictList
//是否正式版。1.正式版。非1，测试版
let isProduce=0;
//以下是正式环境配置，不要乱改。
let apiHost = "http://3m.sx-she.com";
let apiPort = "80";
let apiRoot = "/api";
let staticBaseUrl = 'http://3mfiles.sx-she.com/Files';

if(isProduce!=1){
	//以下是开发环境配置
	// apiHost = "http://127.0.0.1";
	// apiPort = "8086";
	// apiRoot = "/web/api";
	// staticBaseUrl = 'http://3mfiles.sx-she.com/3mFiles';
	apiHost = "http://127.0.0.1";
	apiPort = "9999";
	apiRoot = "/api";
	staticBaseUrl = 'http://127.0.0.1:9999/Files';
/* 	apiHost = "http://172.20.10.2";
	apiPort = "9991";
	apiRoot = "/api";
	staticBaseUrl = 'http://172.20.10.2:9991/3mFiles'; */
}

let apiBaseUrl = apiHost+':'+apiPort+apiRoot;
//用于在开发时修改测试H5地址时使用的默认地址。L
let appH5Url = "http://3m.sx-she.com:80/3mApp";
//放缓存方便原生app使用
var _apiBaseUrl = uni.getStorageSync('apiBaseUrl');
var _appH5Url = uni.getStorageSync('appH5Url');
if('http://3m.sx-she.com'!=_apiBaseUrl){
	//适配发布版本时加错配置
	uni.removeStorageSync('apiBaseUrl');
}
if(!_apiBaseUrl){
	uni.setStorageSync('apiBaseUrl',apiBaseUrl);
}else{
	apiBaseUrl = _apiBaseUrl;
}
if(!_appH5Url){
	uni.setStorageSync('appH5Url',appH5Url);
}else{
	appH5Url = _appH5Url;
}
export const config={
    apiHost,    
    apiPort,  
    apiRoot,  
    apiBaseUrl,
    staticBaseUrl
}