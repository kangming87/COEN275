package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Mapper.DealPriceQuantityMapper;
import coen275.stockmarket.Mapper.StockInfoMapper;
import coen275.stockmarket.Mapper.UserBuyInfoMapper;
import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.Service.BuyService;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuyServiceImpl implements BuyService {


    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DealPriceQuantityMapper dealMapper;

    @Autowired
    UserBuyInfoMapper buyMapper;

    @Autowired
    StockInfoMapper stockInfoMapper;

    @Autowired
    UserBuyInfoMapper userBuyInfoMapper;

    @Override
    public Boolean buyStock(UserBuyInfo userBuyInfo) {
        UserInfo userInfo = userInfoService.getUserInfoService(userBuyInfo.getUserId());

        if(userInfo.getCash() >= userBuyInfo.getBuyPrice() * userBuyInfo.getQuantity()){
            userInfo.setCash(userInfo.getCash() - userBuyInfo.getBuyPrice() * userBuyInfo.getQuantity());
//           List<DealPriceQuantity> dealPriceQuantityList = dealMapper.getUserStockList(userBuyInfo.getStockId(), userInfo.getUserId());
            List<UserStocksInfo> userStocksInfoList = new ArrayList<>();
            userBuyInfoMapper.insert(userBuyInfo);
            Long dealId = userBuyInfo.getId();
            //.out.println(dealId);
//            StockInfo stockInfo = stockInfoMapper.selectByPrimaryKey(userBuyInfo.getStockId());
//            for(DealPriceQuantity dealPriceQuantity : dealPriceQuantityList){
//                UserStocksInfo userStocksInfo = new UserStocksInfo();
//                userStocksInfo.setDealId(dealId);
//                userStocksInfo.setUserId(dealPriceQuantity.getUserId());
//                userStocksInfo.setStockId(dealPriceQuantity.getStockId());
//                userStocksInfo.setStockCode(stockInfo.getStockCode());
//                userStocksInfo.setStockName(stockInfo.getStockName());
//                userStocksInfo.setQuantity(dealPriceQuantity.getQuantity());
//                userStocksInfo.setStatus(dealPriceQuantity.getStatus());
//                userStocksInfo.setPrice(dealPriceQuantity.getPrice());
//                userStocksInfoList.add(userStocksInfo);
//            }

            UserStocksInfo userStocksInfo = new UserStocksInfo();
            userStocksInfo.setDealId(dealId);
            userStocksInfo.setUserId(userBuyInfo.getUserId());
            userStocksInfo.setStockId(userBuyInfo.getStockId());
            userStocksInfo.setStockCode(userBuyInfo.getStockCode());
            userStocksInfo.setStockName(userBuyInfo.getStockName());
            userStocksInfo.setQuantity(userBuyInfo.getQuantity());
            userStocksInfo.setStatus(StockStatusEnum.Buy);
            userStocksInfo.setPrice(userBuyInfo.getBuyPrice());

            System.out.println(userStocksInfo);


            userStocksInfoList.add(userStocksInfo);

            userInfoMapper.updateUserInfo(userInfo); //更改用户金钱信息
            int res = dealMapper.insertDealInfo(userStocksInfo); //插入交易记录
            return true;
        }else{
            return false;
        }
    }

}
