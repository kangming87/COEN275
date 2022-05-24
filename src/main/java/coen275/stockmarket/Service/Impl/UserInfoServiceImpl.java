package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.utils.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl {
    @Autowired
    private UserInfoMapper userMapper;


    //注册
    public LoginResult regist(UserInfo user){
        LoginResult result = new LoginResult();
        result.setSuccess(false);
        try {
            UserInfo existUser = userMapper.findUserByName(user.getUserName());
            if(existUser != null){
                //如果用户名已存在
                result.setMsg("Username is already used.");
            }else{
                userMapper.regist(user);
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
            Long userId = userMapper.login(user);
            if(userId == null){
                result.setMsg("Username or password wrong");
            }else{
                result.setMsg("Login Successfully");
                result.setSuccess(true);
                user.setId(userId);
            }
        } catch(Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}

