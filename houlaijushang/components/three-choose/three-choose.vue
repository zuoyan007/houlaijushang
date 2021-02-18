<template>
    <view class="class_mulu">
        <view class="tree_mulu">
            <view v-for="(index,ind) in mulunum">
                <view class="mulu_frist solid-bottom" @click="fristZhankai(ind)">
                    <text>{{index[tileName ? tileName : 'courseTitle']}}</text>
                    <image v-if='index.open==false' class="fristjiantou" src="./img/right.png" mode=""></image>
                    <image v-if='index.open' class="fristjiantou" src="./img/down.png" mode=""></image>
                </view>
                <view v-if='index.open'>
                    <view class="mulu_second " v-for="(index2,ind2) in mulunum[ind].childrens">
                        <view class="mulu_second_view solid-bottom" @click="muluSecond(ind,ind2)">
                            <text>{{index2[tileName ? tileName : 'ccName']}}</text>
                            <image v-if='index2.secondOpen==false' class="fristjiantou" src="./img/right.png"
                                   mode=""></image>
                            <image v-if='index2.secondOpen' class="fristjiantou" src="./img/down.png" mode=""></image>
                        </view>
                        <view v-if="index2.secondOpen==true" class="mulu_third  solid-bottom"
                              v-for="(index3,ind3) in index2.childrens" @click="choosedVideofn(ind,ind2,ind3)">
                            <view class="third_title">{{index3[tileName ? tileName : 'vTitle']}}</view>
                            <!-- <text class="third_time" v-if="index3.thirdOpen==false">{{index3.duration}}</text>
                            <text class="third_time" v-if="index3.thirdOpen==true">{{index3.duration}}</text> -->
                        </view>
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {}
        },
        props: ["mulunum", "fatherToInfo", 'taskId', 'tileName'],
        onLoad(options) {
        },
        methods: {
            // 下拉
            fristZhankai(ind) {
                if (this.fatherToInfo.shoufenq) {
                    this.mulunum.forEach((index, xb) => {
                        if (ind == xb) {
                            this.$set(index, 'open', !index.open)
                        } else {
                            this.$set(index, 'open', false)
                        }
                    })
                } else {
                    if (this.mulunum[ind].open == true) {
                        this.$set(this.mulunum[ind], 'open', false)
                    } else {
                        this.$set(this.mulunum[ind], 'open', true)
                    }
                }


            },
            // 二级下拉
            muluSecond(ind, ind2) {
                if (this.fatherToInfo.twoshoufenq) {
                    this.mulunum[ind].childrens.forEach((index2, xb2) => {
                        if (ind2 == xb2) {
                            this.$set(index2, 'secondOpen', !index2.secondOpen)
                        } else {
                            this.$set(index2, 'secondOpen', false)
                        }
                    })
                } else {
                    if (this.mulunum[ind].childrens[ind2].secondOpen == true) {
                        this.mulunum[ind].childrens[ind2].secondOpen = false;
                    } else {
                        this.mulunum[ind].childrens[ind2].secondOpen = true;
                    }
                }
            },
            choosedVideofn(ind, ind2, ind3) {
                let that = this;
                that.mulunum.forEach((arr, xiab) => {
                    arr.childrens.forEach((arr2, xiab2) => {
                        arr2.childrens.forEach((arr3, xiab3) => {
                            that.$set(arr3, 'thirdOpen', false)
                        })
                    })
                });
                that.$set(that.mulunum[ind].childrens[ind2].childrens[ind3], 'thirdOpen', true);
                let checkGroupId = that.mulunum[ind].childrens[ind2].childrens[ind3].checkGroupId;
                uni.navigateTo({
                    url: '/pages/check/checkList?checkGroupId=' + checkGroupId + "&taskId=" + this.taskId
                })

            }
        },
    }
</script>

<style lang="scss">
    .class_mulu {
        width: 100%;

        .jieshao_class {
            width: 100%;
            height: 84rpx;
            line-height: 84rpx;
            padding: 0 20rpx;
            box-sizing: border-box;

            .jieshao_title {
                font-size: 26rpx;
                color: #333333;
                font-weight: bold;
                margin-left: 75rpx;
            }

            .jieshao_time {
                font-size: 24rpx;
                color: #333333;
                float: right;
            }

            .choosedcolor {
                color: #3c87ca;
            }
        }

        .tree_mulu {
            width: 100%;
            padding: 20rpx 0;

            .mulu_frist {
                width: 100%;
                padding: 0 20rpx;
                box-sizing: border-box;
                position: relative;
                height: 80rpx;

                text {
                    font-size: 30rpx;
                    color: #000;
                    font-weight: bold;
                    // margin-left: 55rpx;
                    line-height: 80rpx;
                }

                .fristImg {
                    width: 24rpx;
                    height: 24rpx;
                    position: absolute;
                    top: 0;
                    bottom: 0;
                    left: 36rpx;
                    margin: auto;
                }

                .fristjiantou {
                    width: 20rpx;
                    height: 20rpx;
                    position: absolute;
                    top: 0;
                    bottom: 0;
                    right: 44rpx;
                    margin: auto;
                }
            }

            .mulu_second {
                width: 100%;

                .mulu_second_view {
                    width: 100%;
                    padding: 20rpx 20rpx 20rpx 40rpx;
                    box-sizing: border-box;
                    position: relative;
                }
                .fristImg {
                    width: 24rpx;
                    height: 24rpx;
                    position: absolute;
                    top: 0;
                    bottom: 0;
                    left: 50rpx;
                    margin: auto;
                }

                text {
                    font-size: 28rpx;
                    font-weight: bold;
                    color: #333333;
                    // margin-left: 10px;
                }

                .fristjiantou {
                    width: 20rpx;
                    height: 20rpx;
                    position: absolute;
                    top: 0;
                    bottom: 0;
                    right: 44rpx;
                    margin: auto;
                }
            }

            .mulu_third {
                width: 100%;
                // padding: 0 40rpx;
                padding: 0 20rpx 0 84rpx;
                box-sizing: border-box;
                position: relative;
                height: 80rpx;

                .playIngImg {
                    width: 20rpx;
                    height: 20rpx;
                    // margin: auto;
                    margin-right: 20rpx;
                }

                .third_title {
                    font-size: 26rpx;
                    color: #333;
                    // width: 60%;
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: inline-flex;
                    margin-left: 0;
                    line-height: 80rpx;
                }

                .third_time {
                    font-size: 24rpx;
                    color: #666;
                    line-height: 70rpx;
                    float: right;
                }

                .choosedvideo {
                    color: #3c87ca;
                }
            }
        }
    }
</style>