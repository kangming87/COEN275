package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Mapper.DealMapper;
import coen275.stockmarket.Mapper.UserInfoMapper;
import coen275.stockmarket.Service.SaleService;
import coen275.stockmarket.Service.StockService;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.data.UserSaleInfo;
import coen275.stockmarket.data.UserStocksInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

public class SaleServiceImpl implements SaleService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DealMapper dealMapper;

    @Override
    public Boolean saleStock(UserSaleInfo userSaleInfo) {
        UserInfo userInfo = userInfoService.getUserInfoService();
        List<UserStocksInfo> userStocksInfoList = userInfo.getUserStocksInfoList();
        for (UserStocksInfo userStocksInfo : userStocksInfoList) {
            if (userStocksInfo.getStockId().equals(userSaleInfo.getStockId())) {
                if (userStocksInfo.getQuantity() >= userSaleInfo.getQuantity()) {
                    userInfo.setCash(userInfo.getCash() + userSaleInfo.getSalePrice() * userSaleInfo.getQuantity());
                    userStocksInfo.setQuantity(userStocksInfo.getQuantity() - userSaleInfo.getQuantity());
                    userStocksInfo.setStatus(StockStatusEnum.Sale);

                    userInfoMapper.updateUserInfo(userInfo); //更改用户金钱信息
                    int res = dealMapper.insertDealInfo(userStocksInfo); //插入交易记录
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
