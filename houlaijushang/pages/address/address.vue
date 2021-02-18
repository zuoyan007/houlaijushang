<template>
	<view class="content b-t">
		<view class="list b-b" v-for="(item, index) in list" :key="index" @click="checkAddress(item)">
			<view class="wrapper">
				<view class="address-box">
					<text v-if="item.isDefault==0" class="tag">默认</text>
					<text class="address">{{item.userAddress}} {{item.userDoorplate}}</text>
				</view>
				<view class="u-box">
					<text class="name">{{item.userName}}</text>
					<text class="mobile">{{item.userMobile}}</text>
				</view>
			</view>
			<text class="yticon icon-bianji" @click.stop="addAddress(1,item.addressId)"></text>
		</view>
<!-- 		<text style="display:block;padding: 16upx 30upx 10upx;lihe-height: 1.6;color: #fa436a;font-size: 24upx;">
			重要：添加和修改地址回调仅增加了一条数据做演示，实际开发中将回调改为请求后端接口刷新一下列表即可
		</text> -->
		
		<button class="add-btn" @click="addAddress(0)">新增地址</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				source: 0,
				
				list:[],
			}
		},
		onLoad(option){
			console.log(option.source);
			this.source = option.source;
			
			this.doget();
		},
		onShow() {
			this.doget();
		},
		methods: {
			async doget(){
				const res = await this.$myRequest({
					url:"pe/UserAddress/list?1=1",
					data:{
					}
				});
				this.list=res.data;
				uni.removeStorageSync('Address');
				for(var i =0 ; i < this.list.length ; i++){
					if(this.list[i].isDefault=="0"){
						uni.setStorageSync('Address', this.list[i]);
					}
				}
				if(uni.getStorageSync('Address')==undefined||uni.getStorageSync('Address')==''){
					uni.setStorageSync('Address', this.list[0]);
				}
			},
			
			//选择地址
			checkAddress(item){
				if(this.source == 1){
					//this.$api.prePage()获取上一页实例，在App.vue定义
					uni.setStorageSync('AddressNew', item);
					uni.navigateBack()
				}
			},
			addAddress(isInsert,addressId){
				uni.navigateTo({
					url: `/pages/address/addressManage?isInsert=`+isInsert+'&addressId='+addressId
				})
			},
			//添加或修改成功之后回调
			refreshList(data, type){
				//添加或修改后事件，这里直接在最前面添加了一条数据，实际应用中直接刷新地址列表即可
				this.addressList.unshift(data);
				
			}
		}
	}
</script>

<style lang='scss'>
	page{
		padding-bottom: 120upx;
	}
	.content{
		position: relative;
	}
	.list{
		display: flex;
		align-items: center;
		padding: 20upx 30upx;;
		background: #fff;
		position: relative;
	}
	.wrapper{
		display: flex;
		flex-direction: column;
		flex: 1;
	}
	.address-box{
		display: flex;
		align-items: center;
		.tag{
			font-size: 24upx;
			color: $base-color;
			margin-right: 10upx;
			background: #fffafb;
			border: 1px solid #ffb4c7;
			border-radius: 4upx;
			padding: 4upx 10upx;
			line-height: 1;
		}
		.address{
			font-size: 30upx;
			color: $font-color-dark;
		}
	}
	.u-box{
		font-size: 28upx;
		color: $font-color-light;
		margin-top: 16upx;
		.name{
			margin-right: 30upx;
		}
	}
	.icon-bianji{
		display: flex;
		align-items: center;
		height: 80upx;
		font-size: 40upx;
		color: $font-color-light;
		padding-left: 30upx;
	}
	
	.add-btn{
		position: fixed;
		left: 30upx;
		right: 30upx;
		bottom: 16upx;
		z-index: 95;
		display: flex;
		align-items: center;
		justify-content: center;
		width: 690upx;
		height: 80upx;
		font-size: 32upx;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);		
	}
</style>
