<template>
	<view class="app">
		<view class="price-box">
			<text>支付金额</text>
			<text class="price">{{goodsPrice}}</text>
		</view>

		<view class="pay-type-list">

			<view class="type-item b-b" @click="changePayType(1)">
				<text class="icon yticon icon-weixinzhifu"></text>
				<view class="con">
					<text class="tit">微信支付</text>
					<text>推荐使用微信支付</text>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType == 1' />
					</radio>
				</label>
			</view>
			<view class="type-item b-b" @click="changePayType(2)">
				<text class="icon yticon icon-alipay"></text>
				<view class="con">
					<text class="tit">支付宝支付</text>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType == 2' />
					</radio>
				</label>
			</view>
			<view class="type-item" @click="changePayType(3)">
				<text class="icon yticon icon-erjiye-yucunkuan"></text>
				<view class="con">
					<text class="tit">预存款支付</text>
					<text>可用余额 ¥{{prestoreSum}}</text>
				</view>
				<label class="radio">
					<radio value="" color="#fa436a" :checked='payType == 3' />
					</radio>
				</label>
			</view>
		</view>
		
		<text class="mix-btn" @click="pay">确认支付</text>
	</view>
</template>

<script>

	export default {
		data() {
			return {
				payType: 1,
				orderInfo: {},
				
				
				encryptPrice:'',
				goodsPrice:'',
				label:'',
				prestoreSum:0,
				grouponId:'',
			};
		},
		computed: {
		
		},
		onLoad(options) {
			this.label = options.label;
			this.parentOrderId = options.parentOrderId;
			if(this.label==4){
				this.dogetOrder()
			}else{
				this.encryptPrice = options.encryptPrice;
				this.goodsPrice = options.goodsPrice;
				this.grouponId=options.grouponId;
			}
			this.dogetPrestore();
		},
		
		methods: {
			
			async dopay(){
				const res = await this.$myRequest({
					url:"pe/UserPrestore/pay?1=1",
					data:{
						grouponId:this.grouponId,
						payType:this.payType,
						label:this.label,
						encryptPrice:this.encryptPrice,
						parentOrderId:this.parentOrderId,
						
					}
				});
				if(res.data==false){
					this.$api.msg('预存款余额不足');
				}else if(res.data==true){
					uni.redirectTo({
						url: '/pages/money/paySuccess'
					})
				}else{
					this.$api.msg(res.data);
				}
			},
			//获取用户预存款
			async dogetPrestore(){
				const res = await this.$myRequest({
					url:"pe/UserPrestore/get?1=1",
					data:{}
				});
				this.prestoreSum =res.data.prestoreSum.toFixed(2);
			},
			//获取订单详情
			async dogetOrder(){
				const res = await this.$myRequest({
					url:"od/UserParentOrder/get?1=1",
					data:{
						parentOrderId:this.parentOrderId,
					}
				});
				this.encryptPrice = res.data.encryptPrice;
				this.goodsPrice = res.data.totalPaid;
			},
			
			//选择支付方式
			changePayType(type) {
				
				this.payType = type;
			},
			pay(){
				if(this.prestoreSum<this.goodsPrice){
					this.$api.msg('预存款余额不足');
					return;
				}
				this.dopay();
			},
			
			//确认支付
			confirm: async function() {
				uni.redirectTo({
					url: '/pages/money/paySuccess'
				})
			},
		}
	}
</script>

<style lang='scss'>
	.app {
		width: 100%;
	}

	.price-box {
		background-color: #fff;
		height: 265upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		font-size: 28upx;
		color: #909399;

		.price{
			font-size: 50upx;
			color: #303133;
			margin-top: 12upx;
			&:before{
				content: '￥';
				font-size: 40upx;
			}
		}
	}

	.pay-type-list {
		margin-top: 20upx;
		background-color: #fff;
		padding-left: 60upx;
		
		.type-item{
			height: 120upx;
			padding: 20upx 0;
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding-right: 60upx;
			font-size: 30upx;
			position:relative;
		}
		
		.icon{
			width: 100upx;
			font-size: 52upx;
		}
		.icon-erjiye-yucunkuan {
			color: #fe8e2e;
		}
		.icon-weixinzhifu {
			color: #36cb59;
		}
		.icon-alipay {
			color: #01aaef;
		}
		.tit{
			font-size: $font-lg;
			color: $font-color-dark;
			margin-bottom: 4upx;
		}
		.con{
			flex: 1;
			display: flex;
			flex-direction: column;
			font-size: $font-sm;
			color: $font-color-light;
		}
	}
	.mix-btn {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 630upx;
		height: 80upx;
		margin: 80upx auto 30upx;
		font-size: $font-lg;
		color: #fff;
		background-color: $base-color;
		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
	}

</style>
