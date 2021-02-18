/**
 * 数据字典工具类
 * @author:gexu
 * @date 2020-08-17
 */



import {myRequest} from 'common/js/httpKit.js'
import moment from 'moment'; 
import 'moment/locale/zh-cn'
moment.locale('zh-cn');

export const dictKit =  {
	 initDict:async (dictType)=>{
		var newDate = moment().format('YYYY-MM-DD HH:mm:ss');
		var updateDate = uni.getStorageSync('updateDate');
		const res = await myRequest({
			url:"sys/sysDict/dictType?1=1",
			data:{
				updateDate:updateDate||''
			}
		});
		var list = res.data.list;
		for(var i=0;i<list.length;i++){
			var curDictType = list[i].dictType;
			dictKit.initByType(curDictType);
			if(i==list.length-1){
				uni.setStorageSync('updateDate',newDate);
			}
		}
	},
	initByType:async (dictType)=>{
		const res2 = await myRequest({
			url:"sys/sysDict/dictList?1=1",
			data:{
				dictType:dictType||''
			}
		});
		var list2= res2.data.list;
		uni.setStorageSync('dict-'+dictType,JSON.stringify(list2));
		for(var j=0;j<list2.length;j++){
			var dictValue = list2[j].dictValue;
			var dictLabel = list2[j].dictLabel;
			uni.setStorageSync('dict-'+dictType+dictValue, dictLabel);
		}
		return list2;
	},
	getLabel:(dictType,dictValue,defaultLabel)=>{
		if(!dictValue){
			return defaultLabel||'未知';
		}
		var v  =  uni.getStorageSync('dict-'+dictType+dictValue);
		return v||defaultLabel||'未知';
	},
	getList:(dictType)=>{
		var list  =  uni.getStorageSync('dict-'+dictType);
		if(!list){
			const list2 = dictKit.initByType(dictType);
			return list2;
		}
		return JSON.parse(list);
	},
}