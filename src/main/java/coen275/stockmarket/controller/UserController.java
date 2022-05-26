package coen275.stockmarket.controller;

import coen275.stockmarket.Service.Impl.UserInfoServiceImpl;
import coen275.stockmarket.data.StockInfo;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.utils.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserInfoServiceImpl userService;

    //注册
    @PostMapping(value = "/regist")
    public LoginResult regist(UserInfo user){
        return userService.regist(user);
    }

    //登录
    @PostMapping(value = "/login")
    public LoginResult login(UserInfo user){
        return userService.login(user);
    }

    @GetMapping(value = "/getProfile")
    public UserInfo getUserInfo(@PathVariable("userId") Long userId){
        return userService.getUserInfoService(userId);

    }
}
