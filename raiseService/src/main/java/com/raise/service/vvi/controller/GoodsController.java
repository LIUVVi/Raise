package com.raise.service.vvi.controller;

import com.raise.service.vvi.entity.Goods;
import com.raise.service.vvi.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/22
 * @since 1.0
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "progressing", method = RequestMethod.GET)
    public List<Goods> progressing(){
        return goodsService.queryAll();
    }

    @RequestMapping(value = "addgoods", method = RequestMethod.POST)
    public String progressing(@RequestBody Goods goods){
        int orderId = 0;
        try {
            //添加操作返回的是受影响的行数
            orderId = goodsService.insert(goods);
            if (orderId == 0) {
                // 添加失败
                System.out.println("新增失败");
                return ("新增失败");
            } else {
                // 添加成功
                System.out.println("新增成功");
                return ("新增成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("新增失败");
            return ("新增失败");
        }
    }

    @RequestMapping(value = "raise", method = RequestMethod.POST)
    public String raise(@RequestBody Goods goods){
        int orderId = 0;
        try {
            //添加操作返回的是受影响的行数
            orderId = goodsService.update(goods.getId(),goods.getRaised());
            if (orderId == 0) {
                // 添加失败
                System.out.println("捐赠失败");
                return ("捐赠失败");
            } else {
                // 添加成功
                System.out.println("捐赠成功");
                return ("捐赠成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("捐赠失败");
            return ("捐赠失败");
        }
    }


}
