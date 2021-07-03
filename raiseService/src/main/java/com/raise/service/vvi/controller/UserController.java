package com.raise.service.vvi.controller;

import com.raise.service.vvi.entity.User;
import com.raise.service.vvi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MeCity
 * @version V1.0.0
 * @description
 * @date 2020/12/18
 * @since 1.0
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        int orderId = 0;
        try {
            //添加操作返回的是受影响的行数
            orderId = userService.insert(user);
            if (orderId == 0) {
                // 添加失败
                System.out.println("注册失败");
                return ("注册失败");
            } else {
                // 添加成功
                System.out.println("注册成功");
                return ("注册成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("注册失败");
            return ("注册失败");
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        int orderId = 0;
        try {
            //添加操作返回的是受影响的行数
            orderId = userService.query(user.getUsername(),user.getPassword(),user.getLevel());
            if (orderId == 1) {
                // 返回1条数据
                System.out.println("登录成功");
                return ("登录成功");
            } else {
                System.out.println("登录失败");
                return ("登录失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("登录失败");
            return ("登录失败");
        }
    }


    @RequestMapping(value = "test", method = RequestMethod.GET)
    public void sout() {
        System.out.println("dsfasdfsf");
    }

}
