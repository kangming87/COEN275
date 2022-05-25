package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.utils.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired(required = false)
    UserInfoMapper userInfoMapper;


    //注册
    public LoginResult regist(UserInfo user){
        LoginResult result = new LoginResult();
        result.setSuccess(false);
        try {
            UserInfo existUser = userInfoMapper.findUserByName(user.getUsername());
            if(existUser != null){
                //如果用户名已存在
                result.setMsg("Username is already used.");
            }else{
                userInfoMapper.regist(user);
                result.setMsg("Register Success");
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //登录
    public LoginResult login(UserInfo user){
        LoginResult result = new LoginResult();
        result.setSuccess(false);
        try{
            Long userId = userInfoMapper.login(user);
            if(userId == null){
                result.setMsg("Username or password wrong");
            }else{
                result.setMsg("Login Successfully");
                result.setSuccess(true);
                user.setUserId(userId);
            }
        } catch(Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public UserInfo getUserInfoService(Long userId) {
        return userInfoMapper.getUserInfoService(userId);
    }
}

