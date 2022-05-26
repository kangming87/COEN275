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
    @PostMapping(value = "/register/{username}/{password}")
    public LoginResult register(@PathVariable("username")String username, @PathVariable("password")String password){
        return userService.register(username, password);
    }

    //登录
    @PostMapping(value = "/login/{username}/{password}")
    public LoginResult login(@PathVariable("username")String username, @PathVariable("password")String password){
        return userService.login(username, password);
    }

    @GetMapping(value = "/getProfile/{userId}")
    public UserInfo getUserInfo(@PathVariable("userId") Long userId){
        return userService.getUserInfoService(userId);

    }
}
