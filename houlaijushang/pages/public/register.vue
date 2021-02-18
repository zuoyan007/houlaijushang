<template>
	<view class="container">
		<view class="left-bottom-sign"></view>
		<view class="back-btn yticon icon-zuojiantou-up" @click="navBack"></view>
		<view class="right-top-sign"></view>
		<!-- 设置白色背景防止软键盘把下部绝对定位元素顶上来盖住输入框等 -->
		<view class="wrapper">
			<view class="welcome">
				欢迎注册新用户！
			</view>
			<view class="input-content">
				<view class="input-item">
					<text class="tit">用户名</text>
					<input 
						type="text" 
						:value="username" 
						placeholder="请输入用户名"
						maxlength="20"
						data-key="username"
						@input="inputChange"
					/>
				</view>
				<view class="input-item">
					<text class="tit">手机号码</text>
					<input 
						type="number" 
						:value="mobile" 
						placeholder="请输入手机号码"
						maxlength="11"
						data-key="mobile"
						@input="inputChange"
					/>
				</view>
				<view class="flex">
				<view class="input-item " style=" width: 750upx;" >
				
						<text class="tit">邮箱</text>
						<input 
							type="text" 
							:value="email" 
							placeholder="请输入邮箱"
							maxlength="30"
							data-key="email"
							@input="inputChange"
						/>
				</view>
				
				<view style=" height: 120upx; width: 110upx; text-align: center; padding-top: 20upx; border-radius: 13%;" class="bg-grey " @click="checkemail">
					邮箱验证
				</view>
		<!-- 		<button class="cu-btn bg-grey " style="height: 120upx;" >邮箱验证码</button> -->
				</view>
				
				<view class="input-item">
					<text class="tit">邮箱验证码</text>
					<input 
						type="number" 
						:value="emailRandom" 
						placeholder="请输入邮箱验证码"
						maxlength="6"
						data-key="emailRandom"
						@input="inputChange"
					/>
				</view>
				<view class="input-item">
					<text class="tit">密码</text>
					<input 
						type="mobile" 
						value="" 
						placeholder="6-18位不含特殊字符的数字、字母组合"
						placeholder-class="input-empty"
						maxlength="20"
						password 
						data-key="password"
						@input="inputChange"
						@confirm="toLogin"
					/>
				</view>
			</view>
			<button class="confirm-btn" @click="dogetUserEnroll" >马上注册</button>
		</view>
	</view>
</template>

<script>
	import {  
        mapMutations  
    } from 'vuex';
	
	export default{
		data(){
			return {
				userCode:this.$util.guid(),
				username:'',
				mobile: '',
				password: '',
				email:'',
				random:'',
				emailRandom:'',
				logining: false
			}
		},
		onLoad(){
			
		},
		methods: {
			inputChange(e){
				const key = e.currentTarget.dataset.key;
				this[key] = e.detail.value;
			},
			checkemail(){
			　　var reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/; //正则表达式
			　　var obj = this.email; //要验证的对象
			　　if(obj === ""){ //输入不能为空
					this.$api.msg('输入不能为空!');
			　　}else if(!reg.test(obj)){ //正则验证不通过，格式不对
					this.$api.msg('请输入正确的邮箱号!');
			　　}else{
			　　　　this.dogetUserEmail(obj);
			　　}
			},
			//请求邮箱验证码接口
			async dogetUserEmail(email){
				const res = await this.$myRequest({
					url:"sys/sysEmpUser/userEmail?1=1",
					data:{
						userEmail:email
					}
				});
				this.random=res.data.toString();
				console.log(this.random);
			},
			
			//请求注册接口
			async dogetUserEnroll(){
				if(this.mobile){
					if (!(/^1[3456789]\d{9}$/.test(this.mobile))) {
						uni.showToast({
							title:'手机号码格式不正确！'
						})
						return;
					}
				}
				if(this.password.length<6){
					uni.showToast({
						title:'密码太弱请重新输入！'
					})
					return;
				}
				console.log(this.mobile);
				console.log(this.password);
				console.log(this.email);
				console.log(this.random);
				console.log(this.emailRandom);
				const res = await this.$myRequest({
					url:"sys/sysEmpUser/userEnroll?1=1",
					data:{
						userCode:this.userCode,
						username:this.username,
						mobile:this.mobile,
						password:this.password,
						email:this.email,
						random:this.random,
						emailRandom:this.emailRandom
					}
				});
				
				uni.navigateBack({
				    delta:1
				});
			},
			navBack(){
				uni.navigateBack();
			},
			
		},

	}
</script>

<style lang='scss'>
	page{
		background: #fff;
	}
	.container{
		padding-top: 115px;
		position:relative;
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: #fff;
	}
	.wrapper{
		position:relative;
		z-index: 90;
		background: #fff;
		padding-bottom: 40upx;
	}
	.back-btn{
		position:absolute;
		left: 40upx;
		z-index: 9999;
		padding-top: var(--status-bar-height);
		top: 40upx;
		font-size: 40upx;
		color: $font-color-dark;
	}
	.left-top-sign{
		font-size: 120upx;
		color: $page-color-base;
		position:relative;
		left: -16upx;
	}
	.right-top-sign{
		position:absolute;
		top: 80upx;
		right: -30upx;
		z-index: 95;
		&:before, &:after{
			display:block;
			content:"";
			width: 400upx;
			height: 80upx;
			background: #b4f3e2;
		}
		&:before{
			transform: rotate(50deg);
			border-radius: 0 50px 0 0;
		}
		&:after{
			position: absolute;
			right: -198upx;
			top: 0;
			transform: rotate(-50deg);
			border-radius: 50px 0 0 0;
			/* background: pink; */
		}
	}
	.left-bottom-sign{
		position:absolute;
		left: -270upx;
		bottom: -320upx;
		border: 100upx solid #d0d1fd;
		border-radius: 50%;
		padding: 180upx;
	}
	.welcome{
		position:relative;
		left: 50upx;
		top: -90upx;
		font-size: 46upx;
		color: #555;
		text-shadow: 1px 0px 1px rgba(0,0,0,.3);
	}
	.input-content{
		padding: 0 60upx;
	}
	.input-item{
		display:flex;
		flex-direction: column;
		align-items:flex-start;
		justify-content: center;
		padding: 0 30upx;
		background:$page-color-light;
		height: 120upx;
		border-radius: 4px;
		margin-bottom: 50upx;
		&:last-child{
			margin-bottom: 0;
		}
		.tit{
			height: 50upx;
			line-height: 56upx;
			font-size: $font-sm+2upx;
			color: $font-color-base;
		}
		input{
			height: 60upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			width: 100%;
		}	
	}

	.confirm-btn{
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin-top: 70upx;
		background: $uni-color-primary;
		color: #fff;
		font-size: $font-lg;
		&:after{
			border-radius: 100px;
		}
	}
	.forget-section{
		font-size: $font-sm+2upx;
		color: $font-color-spec;
		text-align: center;
		margin-top: 40upx;
	}
	.register-section{
		position:absolute;
		left: 0;
		bottom: 50upx;
		width: 100%;
		font-size: $font-sm+2upx;
		color: $font-color-base;
		text-align: center;
		text{
			color: $font-color-spec;
			margin-left: 10upx;
		}
	}
</style>
