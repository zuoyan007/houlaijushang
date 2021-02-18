// //// http://210.76.74.2/yjzf/api/v2/sys/sysUtil/dictList
// const apiHost = "http://192.168.1.186";
// const apiPort = "9991";
// const apiRoot = "/3m/api/";
// const staticBaseUrl = 'http://192.168.1.186:9991/3m';





// const apiBase = apiHost+':'+apiPort+apiRoot;

import {config} from 'static/config/config.js'
export const myRequest = (options) => {
	return new Promise((resolve, reject) => {
		uni.showLoading({
			title: '',
			mask: true
		})
		options.data.appNanoTime = uni.getStorageSync('nanoTime'),
			options.data.appToken = uni.getStorageSync('token'),
			// alert(config.apiBaseUrl);
			uni.request({
				url: config.apiBaseUrl + '/' + options.url + '&appUserId=' + uni.getStorageSync('userId'),
				method: options.method || 'POST',
				header: {
					'content-type': 'application/x-www-form-urlencoded',
					// 'nanoTime': uni.getStorageSync('nanoTime'),
					// 'token': uni.getStorageSync('token'),
				},
				data: options.data || {},
				success: (res) => {
					if (res.data.status !== 1) {
						return uni.showToast({
							title: res.data.message || "获取数据失败"
						})
					}
					if (options.success) {
						options.success(res.data);
					}
					resolve(res.data);
				},
				fail: (error) => {
					console.log('fail');
					uni.showToast({
						title: '服务器约会去啦'
					})
					reject(error);
				},
				complete: () => {
					uni.hideLoading();
				}
			})
	})
}
