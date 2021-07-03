package com.raise.service.vvi.test;

import com.raise.service.vvi.VviApplication;
import com.raise.service.vvi.entity.Goods;
import com.raise.service.vvi.entity.User;
import com.raise.service.vvi.service.GoodsService;
import com.raise.service.vvi.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/18
 * @since 1.0
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = VviApplication.class)
public class UnitTest {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    @Test
    public void test1() {

            int orderId = 0;
            try {
                //添加操作返回的是受影响的行数
                User user = new User();
                user.setUsername("大松");
                user.setPassword("13232000");
                orderId = userService.insert(user);
                if(orderId==0){
                    //添加失败
                    System.out.println("添加失败");
                }else{
                    System.out.println("添加成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("添加失败");
        }
    }

    @Test
    public void test2(){
        List<Goods> goodsList;
        goodsList = goodsService.queryAll();
        System.out.println(goodsList.toString());
    }

    @Test
    public void test3() {

        int orderId = 0;
        try {
            //添加操作返回的是受影响的行数
            Goods goods = new Goods();
            goods.setGoodsName("口罩");
            goods.setLocation("上海");
            goods.setDemand(500);
            goods.setImg_url("R.mipmap.kz");
            orderId = goodsService.insert(goods);
            if(orderId==0){
                //添加失败
                System.out.println("添加失败");
            }else{
                System.out.println("添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加失败");
        }
    }

}


