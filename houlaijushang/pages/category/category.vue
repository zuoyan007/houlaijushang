<template>
	<view class="content">
		<scroll-view class="VerticalNav nav" scroll-y scroll-with-animation  style="height:calc(100vh - 45px); width: 180upx;">
			<view class="bg-white" :class="index==tabCur?'text-green cur bg2':'bg1'" v-for="(item,index) in kmIndustry" :key="index" @tap="TabSelect"
			 :data-id="index">
				<view   class="solid-bottom text-df padding flex" @tap="checkDictCode(item)">
				<view>{{item.dictLabel}}</view>
				</view>
			</view>
		</scroll-view>
		
		<scroll-view class="VerticalMain" scroll-y scroll-with-animation style="height:calc(100vh - 45px)"
		  @scroll="VerticalMain">
		 
			<view >
				<view class="bg-white "  style="height:calc(100vh - 45px)">
					<view class="cu-bar solid-bottom bg-white">
						<view class="bg-white padding" style="width: 100%;">
							<view class="grid margin-bottom text-center col-3">
								<view  v-for="(item1,indexs) in dictCode" :key="indexs">
									<view class="text-center padding-bottom"  @tap="doSelContent(item1.dictValue,item1.dictCode,item1.dictLabel)">
							<view class="cu-avatar  lg  "  style="height: 130upx; margin-bottom: 10upx; margin-right: 10upx; width: 69.33px;" :style="'background-image:url('+(item1.remarks?(url+item1.remarks):'static/img/head.png')+');'"></view>
							
										<view style="height: 50upx; " class="text-black ">{{item1.dictLabel}}</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				url:'../../static/goods_property',
				dictCode:[],
				parentCode:0,
				kmIndustry:[],
				tabCur: 0,

								
								
								
								
								
								
								
								
								
								
								
								itembelong:[{}],
								itembelong_index:0,
								index: 0,
								picker: [],
								curTab: 10,
								list: [],
								tabCur: 0,
								mainCur: 0,
								verticalNavTop: 0,
								load: true,
								
			}
		},
		onLoad(){
			this.doFinishRequest();
		},
		methods: {
			async doFinishRequest(){
				const res = await this.$myRequest({
					url:"sys/sysDict/dictList?1=1",
					data:{
						dictType:'goods_property',
						parentCode:this.parentCode,
					}
				});
				this.kmIndustry=res.data.list;
				if(this.parentCode=='0'){
							this.parentCode=res.data.list[0].dictCode;
							this.doget();
				}
						
			},
			async doget(){
				const res = await this.$myRequest({
					url:"sys/sysDict/dictList?1=1",
					data:{
						dictType:'goods_property',
						parentCode:this.parentCode,
					}
				});
					 this.dictCode=res.data.list;
			},

			
			doSelContent(dictValue,dictCode,dictLabel){
				uni.navigateTo({
					url: '/pages/product/list?dictValue='+dictValue,
					animationDuration: 200
				})
			},
			checkDictCode(e){
				
				this.parentCode=e.dictCode;
				this.title1=e.dictLabel;
				this.doget();
			},
			PickerChange(e) {
				this.index = e.detail.value
			},
			PickerChange1(e) {
				this.itembelong_index = e.detail.value
			},
			openView(itemKey) {
				this.curTab = itemKey;
			},
			TabSelect(e) {
				this.tabCur = e.currentTarget.dataset.id;
				this.mainCur = e.currentTarget.dataset.id;
				this.verticalNavTop = (e.currentTarget.dataset.id - 1) * 50
			},
			navToList(sid, tid){
				uni.navigateTo({
					url: `/pages/product/list?fid=${this.currentId}&sid=${sid}&tid=${tid}`
				})
			}
		}
	}
</script>

<style lang='scss'>
	page,
	.content {
		height: 100%;
		background-color: #f8f8f8;
	}

	.content {
		display: flex;
	}
	.left-aside {
		flex-shrink: 0;
		width: 200upx;
		height: 100%;
		background-color: #fff;
	}
	.f-item {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100%;
		height: 100upx;
		font-size: 28upx;
		color: $font-color-base;
		position: relative;
		&.active{
			color: $base-color;
			background: #f8f8f8;
			&:before{
				content: '';
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				height: 36upx;
				width: 8upx;
				background-color: $base-color;
				border-radius: 0 4px 4px 0;
				opacity: .8;
			}
		}
	}

	.right-aside{
		flex: 1;
		overflow: hidden;
		padding-left: 20upx;
	}
	.s-item{
		display: flex;
		align-items: center;
		height: 70upx;
		padding-top: 8upx;
		font-size: 28upx;
		color: $font-color-dark;
	}
	.t-list{
		display: flex;
		flex-wrap: wrap;
		width: 100%;
		background: #fff;
		padding-top: 12upx;
		&:after{
			content: '';
			flex: 99;
			height: 0;
		}
	}
	.t-item{
		flex-shrink: 0;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
		width: 176upx;
		font-size: 26upx;
		color: #666;
		padding-bottom: 20upx;
		
		image{
			width: 140upx;
			height: 140upx;
		}
	}
	
	.bg1{
		background-color: #f4d5dc;
	}
	.bg2{
		background-color: #FFFFFF;
	}
	.fixed {
		position: fixed;
		z-index: 99;
	}
	
	.VerticalNav.nav {
		width: 200upx;
		white-space: initial;
	}
	
	.VerticalNav.nav .cu-item {
		width: 100%;
		text-align: center;
		background-color: #fff;
		margin: 0;
		border: none;
		height: 50px;
		position: relative;
	}
	
	.VerticalNav.nav .cu-item.cur {
		background-color: #f1f1f1;
	}
	
	.VerticalNav.nav .cu-item.cur::after {
		content: "";
		width: 8upx;
		height: 30upx;
		border-radius: 10upx 0 0 10upx;
		position: absolute;
		background-color: currentColor;
		top: 0;
		right: 0upx;
		bottom: 0;
		margin: auto;
	}
	
	.VerticalBox {
		display: flex;
	}
	
	.VerticalMain {
		background-color: #f1f1f1;
		flex: 1;
	}
</style>
