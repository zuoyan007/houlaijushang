<template>

	<view>
		<form>
			<view class="cu-modal top-modal text-left" :style="[{'padding-top':(this.StatusBar+this.CustomBar) + 'px'}]"
				:class="showQueryBoxFlag==1?'show':''" @tap="hideQueryBox">
				<view class="cu-dialog" @tap.stop="">
					<view class="margin-tb-sm">
						<slot name="queryContent" ></slot>
					</view>
					<!-- <view name="queryContent" class="margin-tb">

					</view> -->
					<view class="cu-bar  ">
						<view class="action  flex" style="width: 100%;">
							<view class="flex-sub"><button  class=" cu-btn line-green text-green" form-type="reset" @tap="doReset">重置</button></view>
							<view class="flex-sub text-right"><button class=" cu-btn bg-green  " @tap="doQuery">确定</button></view>
						</view>
					</view>
				</view>
			</view>
		</form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				showQueryBoxFlag:this.showQueryBoxFlagSub,//防止vue单项数据流语法警告，设置新变量
			};
		},
		props: ["showQueryBoxFlagSub"],
		methods: {
			showQueryBox() {
				this.showQueryBoxFlag = this.showQueryBoxFlag==0?1:0;
			},
			hideQueryBox() {
				this.showQueryBoxFlag = 0
			},
			doQuery(){
				this.hideQueryBox();
				this.$emit('doPullDownRefresh');
			},
			doReset(){
				this.hideQueryBox();
				this.$emit('doResetRefresh');
			}
		}
	}
</script>

<style>

</style>
