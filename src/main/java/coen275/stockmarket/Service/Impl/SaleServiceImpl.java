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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    @Override
    public Boolean saleStock(UserSaleInfo userSaleInfo) {
        UserInfo userInfo = userInfoService.getUserInfoService(userSaleInfo.getUserId());
        List<DealPriceQuantity> dealPriceQuantityList = dealMapper.getUserStockList(userSaleInfo.getStockId(), userInfo.getUserId());
        List<UserStocksInfo> userStocksInfoList = new ArrayList<>();
        StockInfo stockInfo = stockMapper.selectByPrimaryKey(userSaleInfo.getStockId());
        for(DealPriceQuantity dealPriceQuantity : dealPriceQuantityList){
            userStocksInfoList.add(new UserStocksInfo(null, dealPriceQuantity.getDealId(), dealPriceQuantity.getUserId(), dealPriceQuantity.getStockId(),
                    stockInfo.getStockCode(), stockInfo.getStockName(), null, dealPriceQuantity.getQuantity(), dealPriceQuantity.getStatus(),
                    dealPriceQuantity.getPrice()));
        }
        boolean flag = true;
        for (UserStocksInfo userStocksInfo : userStocksInfoList) {
            if (userStocksInfo.getStockId().equals(userSaleInfo.getStockId())) {
                if (userStocksInfo.getQuantity() >= userSaleInfo.getQuantity() && (userStocksInfo.getStatus()
                        == StockStatusEnum.BuySuccess || userStocksInfo.getStatus() == StockStatusEnum.BuyPartSuccess)) {
                    userStocksInfo.setQuantity(userStocksInfo.getQuantity() - userSaleInfo.getQuantity());
                    userStocksInfo.setStatus(StockStatusEnum.Sale);

                    userInfoMapper.updateUserInfo(userInfo); //更改用户金钱信息
                    int res = dealMapper.insertDealInfo(userStocksInfo); //插入交易记录
                    saleMapper.insertUserSale(userSaleInfo); //插入委托卖的价格
                    flag = false;
                    return true;
                } else {
                    throw new CommonException("当前用户没有足够的数量这个股票", "NoEnoughQuantity", 409);
                }
            }
        }
        if(flag){
            throw new CommonException("当前用户没有这个股票", "NoSuchStock", 409);
        }
        return false;
    }
}
