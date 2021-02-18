import {
	myRequest
} from 'common/js/httpKit.js'
import moment from 'moment';
import 'moment/locale/zh-cn'
moment.locale('zh-cn');
import md5 from 'js-md5';
export const util = {
	goBack: function() {
		uni.navigateBack({
			delta: 1
		});
	},
	openView(url) {
		uni.navigateTo({
			url: url
		});
	},
	guid: () => {
		let userId = uni.getStorageSync('userId');
		console.log("Math.random():" + Math.random());
		return md5(userId + moment().format('YYYY-MM-DD HH:mm:ss') + Math.random());
	},
	//金额格式化校验
	moneyFormat(money) {
		return (/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/.test(money));
	},
	//手机号格式校验
	mobileFormat(mobile){
		return (/^1[3456789]\d{9}$/.test(money));
	},
	//电子邮箱格式校验
	emailFormat(email){
		return (/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(money));
	},
	//校验有效的输入框字符
	checkAlidInput(inputVal,length){
		if (inputVal.trim().replace(/\s/g,"").length<=length){
			return true;
		}
		return false;
	},
	//提示框语句
	promptMessage(warring){
		uni.showToast({
			title:warring
		})
	},
}