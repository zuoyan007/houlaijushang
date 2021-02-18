package com.lamb.od.service;

import com.alibaba.fastjson.JSON;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.cons.Dict;
import com.lamb.cp.dao.CouponUserDao;
import com.lamb.cp.entity.CouponUser;
import com.lamb.cp.service.CouponUserService;
import com.lamb.gd.dao.GoodsDetailDao;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.od.entity.CreateOrder;
import com.lamb.pe.dao.UserDiscountsDao;
import com.lamb.pe.entity.UserDiscounts;
import com.lamb.pe.service.UserDiscountsService;
import com.lamb.sys.dao.SysDictDataDao;
import com.lamb.sys.entity.SysDictData;
import com.lamb.sys.entity.SysDictDataVO;
import com.lamb.sys.service.SysDictDataService;
import com.lamb.util.UserKit;
import com.lamb.util.sys.ResultApp;
import com.lamb.util.sys.StringKit;
import org.apache.http.HttpRequest;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly=true)
public class CreateOrderService {
    @Autowired
    private GoodsDetailDao goodsDetailDao;
    @Autowired
    private CouponUserService couponUserService;
    @Autowired
    private UserDiscountsDao userDiscountsDao;
    @Autowired
    private SysDictDataDao sysDictDataDao;


    /**
     * 查询商品总价
     * @param orderList
     * @return
     */
    public Map<Object, Object> CreateOrder(String orderList){
        List<CreateOrder> createOrders = JSON.parseArray(orderList, CreateOrder.class);
        //查询总价
        double goodsPrice = 0;
        double goodsSpecial = 0;
        java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
        HashMap<String, Double> goodsSpecialMap = new HashMap<>();
        for (CreateOrder createOrder : createOrders) {
            GoodsDetail goodsDetail = goodsDetailDao.get(new GoodsDetail().setGoodsId(createOrder.getGoodsId()));
            String substring = goodsDetail.getGoodPrice().substring(1);
            //将现有价格转换为Double类型保留2为小数
            double goodPrice = Double.parseDouble(myformat.format(Double.parseDouble(substring)));
            double Price =goodPrice*(createOrder.getGoodsNumber());
            double special = checkUserDiscounts(UserKit.getUserId(), createOrder.getGoodsId(), Price, goodsDetail.getDiscounts());
            goodsSpecialMap.put(createOrder.getGoodsId(),special);
            goodsPrice+=(Price-special);
            goodsSpecial+=special;
        }
        //查询用户的优惠卷
        List<CouponUser> list = couponUserService.findList(new CouponUser().setUserId(UserKit.getUserId()));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("couponList",list);
        map.put("goodsPrice",Double.parseDouble(myformat.format(goodsPrice)));
        map.put("goodsSpecial",Double.parseDouble(myformat.format(goodsSpecial)));
        map.put("goodsSpecialMap",goodsSpecialMap);
        return map;
    }


    /**
     * 查看单个商品优惠总价
     * @param userId    用户id
     * @param goodsId   商品id
     * @param goodsPrice 当前商品总价
     * @param discounts 商品优惠数据字典
     * @return
     */
    public double checkUserDiscounts(String userId ,String goodsId,double goodsPrice,String discounts){
        double special = 0;
        java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
        UserDiscounts userDiscounts = new UserDiscounts();
        userDiscounts.setUserId(userId);
        userDiscounts.setGoodsId(goodsId);
        UserDiscounts userDiscounts1 = userDiscountsDao.getByEntity(userDiscounts);
        if (userDiscounts1!=null){
            String[] discountsList = discounts.split(",");
            for (int i = 0; i < discountsList.length; i++) {
                SysDictData dictData = new SysDictData();
                dictData.setDictType("discounts_type");
                dictData.setDictValue(discountsList[i]);
                SysDictData byEntity = sysDictDataDao.getByEntity(dictData);
                if (byEntity!=null){
                    String dictLabel = byEntity.getDictLabel();
                    String s1 = StringKit.subString(dictLabel, "满", "元");
                    double satisfy = Double.parseDouble(myformat.format(Double.parseDouble(s1)));

                    String str1=dictLabel.substring(0, dictLabel.indexOf("元"));
                    String str2=dictLabel.substring(str1.length()+1, dictLabel.length());


                    String s2 = StringKit.subString(str2, "减", "元");
                    double subtract = Double.parseDouble(myformat.format(Double.parseDouble(s2)));
                    if (satisfy<goodsPrice&&special<subtract){
                        special=subtract;
                    }
                }
            }
        }
        return special;
    }
}
