package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Mapper.BuyMapper;
import coen275.stockmarket.Mapper.DealMapper;
import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.Service.BuyService;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.data.UserStocksInfo;
import coen275.stockmarket.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

public class BuyServiceImpl implements BuyService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DealMapper dealMapper;

    @Autowired
    BuyMapper buyMapper;

    @Override
    public Boolean buyStock(UserBuyInfo userBuyInfo) {
        UserInfo userInfo = userInfoService.getUserInfoService();
        if(userInfo.getCash() >= userBuyInfo.getBuyPrice() * userBuyInfo.getQuantity()){
            userInfo.setCash(userInfo.getCash() - userBuyInfo.getBuyPrice() * userBuyInfo.getQuantity());
            List<UserStocksInfo> userStocksInfoList = userInfo.getUserStocksInfoList();
            UserStocksInfo userStocksInfo = new UserStocksInfo(userBuyInfo.getUserId(),
                    userBuyInfo.getStockId(),
                    userBuyInfo.getStockCode(), userBuyInfo.getStockName(),
                    new Timestamp(System.currentTimeMillis()), userBuyInfo.getQuantity(),
                    StockStatusEnum.Buy, userBuyInfo.getBuyPrice());
            userStocksInfoList.add(userStocksInfo);

            userInfoMapper.updateUserInfo(userInfo); //更改用户金钱信息
            int res = dealMapper.insertDealInfo(userStocksInfo); //插入交易记录
            buyMapper.insertUserBuy(userBuyInfo); //插入委托买的价格
            return true;
        }else{
            return false;
        }
    }

}
