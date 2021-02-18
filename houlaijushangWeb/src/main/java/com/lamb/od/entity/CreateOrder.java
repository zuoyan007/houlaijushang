package com.lamb.od.entity;

public class CreateOrder {
    private String childOrderId;                //子订单id
    private String goodsId;                     //商品id
    private double goodsNumber;                 //商品数量
    private double goodsSpecial;                //商品促销优惠金额

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public double getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(double goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public double getGoodsSpecial() {
        return goodsSpecial;
    }

    public void setGoodsSpecial(double goodsSpecial) {
        this.goodsSpecial = goodsSpecial;
    }

    public String getChildOrderId() {
        return childOrderId;
    }

    public void setChildOrderId(String childOrderId) {
        this.childOrderId = childOrderId;
    }
}
