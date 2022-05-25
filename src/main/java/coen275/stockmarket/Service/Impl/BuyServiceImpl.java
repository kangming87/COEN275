package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Mapper.BuyMapper;
import coen275.stockmarket.Mapper.DealMapper;
import coen275.stockmarket.Mapper.StockMapper;
import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.Service.BuyService;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.*;
import coen275.stockmarket.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyServiceImpl implements BuyService {

    @Autowired(required = false)
    UserInfoMapper userInfoMapper;

    @Autowired(required = false)
    UserInfoService userInfoService;

    @Autowired(required = false)
    DealMapper dealMapper;

    @Autowired(required = false)
    BuyMapper buyMapper;

    @Autowired(required = false)
    StockMapper stockMapper;

    @Override
    public Boolean buyStock(UserBuyInfo userBuyInfo) {
        UserInfo userInfo = userInfoService.getUserInfoService(userBuyInfo.getUserId());
        if(userInfo.getCash() >= userBuyInfo.getBuyPrice() * userBuyInfo.getQuantity()){
            userInfo.setCash(userInfo.getCash() - userBuyInfo.getBuyPrice() * userBuyInfo.getQuantity());
            List<DealPriceQuantity> dealPriceQuantityList = dealMapper.getUserStockList(userBuyInfo.getStockId(), userInfo.getId());
            List<UserStocksInfo> userStocksInfoList = new ArrayList<>();
            StockInfo stockInfo = stockMapper.getStockInfo(userBuyInfo.getStockId());
            for(DealPriceQuantity dealPriceQuantity : dealPriceQuantityList){
                userStocksInfoList.add(new UserStocksInfo(dealPriceQuantity.getDealId(), dealPriceQuantity.getUserId(), dealPriceQuantity.getStockId(),
                        stockInfo.getStockCode(), stockInfo.getStockName(), null, dealPriceQuantity.getQuantity(), dealPriceQuantity.getStatus(),
                        dealPriceQuantity.getPrice()));
            }
            UserStocksInfo userStocksInfo = new UserStocksInfo(0L, userBuyInfo.getUserId(),
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
