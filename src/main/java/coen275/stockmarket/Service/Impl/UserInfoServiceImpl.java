package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.utils.LoginResult;
import coen275.stockmarket.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;


    //注册
    public LoginResult register(String username, String password){
        LoginResult result = new LoginResult();
        result.setSuccess(false);
        try {
            UserInfo existUser = userInfoMapper.findUserByName(username);
            if(existUser != null){
                //如果用户名已存在
                result.setResult("Username is already used.");
            }else{
                userInfoMapper.register(username, password);
                Long userId = userInfoMapper.login(username, password);
                result.setResult("Success");
                result.setSuccess(true);
                result.setUserId(userId);
            }
        } catch (Exception e) {
            result.setResult(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //登录
    public LoginResult login(String username, String password){
        //UserInfo user = userInfoMapper.findUserByName(username);

        LoginResult result = new LoginResult();
        result.setSuccess(false);
        try{
            Long userId = userInfoMapper.login(username, password);
            if(userId == null){
                result.setResult("Username or password wrong");
            }else{
                result.setResult("Success");
                result.setSuccess(true);
                result.setUserId(userId);
            }
        } catch(Exception e) {
            result.setResult(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //充值
    public SuccessResponse add_cash(Long userId, Double cash){
        SuccessResponse result = new SuccessResponse();
        result.setCode("Success");
        result.setMessage("Cash added");
        result.setStatus(200);
        try{
            UserInfo existUser = userInfoMapper.getUserInfoService(userId);
            existUser.setCash(existUser.getCash() + cash);
            userInfoMapper.updateUserInfo(existUser);
        }catch(Exception e){
            result.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public UserInfo getUserInfoService(Long userId) {
        return userInfoMapper.getUserInfoService(userId);
    }
}

