package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Exception.CommonException;
import coen275.stockmarket.Mapper.DealPriceQuantityMapper;
import coen275.stockmarket.Mapper.StockInfoMapper;
import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.Mapper.UserSaleInfoMapper;
import coen275.stockmarket.Service.SaleService;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaleServiceImpl implements SaleService {
    @Autowired(required = false)
    UserInfoMapper userInfoMapper;

    @Autowired(required = false)
    UserInfoService userInfoService;

    @Autowired(required = false)
    DealPriceQuantityMapper dealMapper;

    @Autowired(required = false)
    UserSaleInfoMapper saleMapper;

    @Autowired(required = false)
    StockInfoMapper stockMapper;

    @Autowired
    UserSaleInfoMapper userSaleInfoMapper;

    @Override
    public Boolean saleStock(UserSaleInfo userSaleInfo) {
        List<DealPriceQuantity> dealPriceQuantityList = dealMapper.getUserStockList(userSaleInfo.getUserId(), userSaleInfo.getStockId());
        System.out.println(dealPriceQuantityList);
        List<UserStocksInfo> userStocksInfoList = new ArrayList<>();
        StockInfo stockInfo = stockMapper.selectByPrimaryKey(userSaleInfo.getStockId());
        for(DealPriceQuantity dealPriceQuantity : dealPriceQuantityList){
            userStocksInfoList.add(new UserStocksInfo(dealPriceQuantity.getId(), dealPriceQuantity.getDealId(), dealPriceQuantity.getUserId(), dealPriceQuantity.getStockId(),
                    stockInfo.getStockCode(), stockInfo.getStockName(), null, dealPriceQuantity.getQuantity(), dealPriceQuantity.getStatus(),
                    dealPriceQuantity.getPrice()));
        }

        boolean flag = true;
        long sum = 0L;
        for (UserStocksInfo userStocksInfo : userStocksInfoList) {
            if (userStocksInfo.getStockId().equals(userSaleInfo.getStockId())) {
                if (userStocksInfo.getQuantity() >= userSaleInfo.getQuantity() && (userStocksInfo.getStatus()
                        == StockStatusEnum.BuySuccess || userStocksInfo.getStatus() == StockStatusEnum.BuyPartSuccess)) {
                    sum += userStocksInfo.getQuantity();
                    flag = false;
                }
            }
        }
        if(flag){
            throw new CommonException("当前用户没有这个股票", "NoSuchStock", 409);
        }
        if(sum < userSaleInfo.getQuantity()){
            throw new CommonException("当前用户没有这个股票或没有这个数量股票", "NoSuchStock", 409);
        }
        userSaleInfoMapper.insert(userSaleInfo);
        Long dealId = userSaleInfo.getId();


        UserStocksInfo userStocksInfo = new UserStocksInfo();
        userStocksInfo.setDealId(dealId);
        userStocksInfo.setUserId(userSaleInfo.getUserId());
        userStocksInfo.setStockId(userSaleInfo.getStockId());
        userStocksInfo.setStockCode(userSaleInfo.getStockCode());
        userStocksInfo.setStockName(userSaleInfo.getStockName());
        userStocksInfo.setQuantity(userSaleInfo.getQuantity());
        userStocksInfo.setStatus(StockStatusEnum.Sale);
        userStocksInfo.setPrice(userSaleInfo.getSalePrice());

        dealMapper.insertDealInfo(userStocksInfo);
//        while(sum != 0){
//            for (UserStocksInfo userStocksInfo : userStocksInfoList) {
//                if (userStocksInfo.getStockId().equals(userSaleInfo.getStockId())) {
//                    if (userStocksInfo.getQuantity() >= userSaleInfo.getQuantity() && (userStocksInfo.getStatus()
//                            == StockStatusEnum.BuySuccess || userStocksInfo.getStatus() == StockStatusEnum.BuyPartSuccess)) {
//
//                        if(userStocksInfo.getQuantity() >= userSaleInfo.getQuantity()){
//                            userStocksInfo.setQuantity(userStocksInfo.getQuantity() - userSaleInfo.getQuantity());
//                            userStocksInfo.setStatus(StockStatusEnum.BuySuccess);
//                            userStocksInfo.setDealId(dealId);
//                            sum = 0;
//                        }else if(userStocksInfo.getQuantity() < userSaleInfo.getQuantity()){
//                            userSaleInfo.setQuantity(userSaleInfo.getQuantity() - userStocksInfo.getQuantity());
//                            userStocksInfo.setQuantity(0);
//                            userStocksInfo.setStatus(StockStatusEnum.BuySuccess);
//                            userStocksInfo.setDealId(dealId);
//                        }
//
//
//                        int res = dealMapper.insertDealInfo(userStocksInfo); //插入交易记录
//                        flag = false;
//                    }
//                }
//            }
//            return true;
//        }

        return false;
    }
}
