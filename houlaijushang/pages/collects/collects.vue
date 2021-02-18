<template>
	<view>
		<view
			class="goods-box-single" style="background-color: #FFFFFF;  padding-left: 35upx;" 
			v-for="(goodsItem, goodsIndex) in list" :key="goodsIndex"   @click="navToDetailPage(goodsItem.goodsId)"
		>
			<image class="goods-img"   :src="($config.staticBaseUrl+goodsItem.imageList[0].fileUrl)" mode="aspectFill"></image>
			<view class="right">
				<text class="title clamp" style="padding-left: 50upx; padding-top: 10upx;">{{goodsItem.goodsDetail.goodsName}}</text>
				<text class="title clamp" style="padding-left: 50upx; padding-top: 10upx;">{{goodsItem.goodsDetail.choose}}</text>
				<text class="attr-box" style="padding-left: 50upx; padding-top: 100upx;"> {{goodsItem.goodsDetail.goodPrice}}</text>
				
		</view>
		</view>
		<view class="text-gray padding text-center"  >没有更多数据了</view>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				list:{},
			}
		},
		onLoad() {
			this.doget();
		},
		methods: {
			async doget(){
				const res = await this.$myRequest({
					url:"pe/UserCollects/list?1=1",
					data:{
					}
				});
				this.list = res.data;
				console.log(res.data);
			},
			navToDetailPage(id){
				uni.navigateTo({
					url: `/pages/product/product?id=`+id
				})

			},
		}
	}
</script>

<style>
.goods-box-single{
			display: flex;
			padding: 20upx 0;
			.goods-img{
				display: block;
				width: 120upx;
				height: 120upx;
			}
			.right{
				flex: 1;
				display: flex;
				flex-direction: column;
				padding: 0 30upx 0 24upx;
				overflow: hidden;
				.title{
					font-size: $font-base + 2upx;
					color: $font-color-dark;
					line-height: 1;
				}
				.attr-box{
					font-size: $font-sm + 2upx;
					color: $font-color-light;
					padding: 10upx 12upx;
				}
				.price{
					font-size: $font-base + 2upx;
					color: $font-color-dark;
					&:before{
						content: '￥';
						font-size: $font-sm;
						margin: 0 2upx 0 8upx;
					}
				}
			}
		}
		.title{
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			line-height: 1;
		}
		.attr-box{
			font-size: $font-sm + 2upx;
			color: $font-color-light;
			padding: 10upx 12upx;
		}
		.price{
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			&:before{
				content: '￥';
				font-size: $font-sm;
				margin: 0 2upx 0 8upx;
			}
		}
		.goods-img{
			display: block;
			width: 150upx;
			height: 150upx;
		}
</style>

