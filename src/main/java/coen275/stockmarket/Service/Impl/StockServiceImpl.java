package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Mapper.*;
import coen275.stockmarket.Service.StockService;
import coen275.stockmarket.data.*;
import coen275.stockmarket.utils.stock_and_profit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class StockServiceImpl implements StockService {
    @Autowired
    StockInfoMapper stockInfoMapper;

    @Autowired
    StockInfoDetailMapper stockDetailMapper;

    @Autowired
    DealPriceQuantityMapper dealMapper;

    @Autowired
    UserBuyInfoMapper userBuyInfoMapper;

    @Autowired
    UserSaleInfoMapper userSaleInfoMapper;

    @Override
    public StockInfo getStockInfo(Long stockId) {
        StockInfo stockInfo = stockInfoMapper.selectByPrimaryKey(stockId);
        return stockInfo;
    }

    @Override
    public List<StockInfo> getStockInfoList() {
        StockInfoExample stockInfoExample = new StockInfoExample();
        return stockInfoMapper.selectByExample(stockInfoExample);
    }

    @Override
    public List<stock_and_profit> getSuggestionStocks() {

        return stockInfoMapper.selectforSuggestion();
    }

    @Override
    public StockInfoDetail getStockDetailInfo(Long stockId) {
        return stockDetailMapper.getStockInfoDetail(stockId);
    }

    @Override
    public List<DealPriceQuantity> getStockTradeList(Long stockId) {
        return dealMapper.getStockTradeList(stockId);
    }

    @Override
    public List<DealPriceQuantity> getUserTradeList(Long userId) {
        List<DealPriceQuantity> dealPriceQuantityList =  dealMapper.getUserTradeList(userId);
//        for(DealPriceQuantity dealPriceQuantity : dealPriceQuantityList){
//            if(dealPriceQuantity.getStatus() == StockStatusEnum.BuyPartSuccess){
//                long id = dealPriceQuantity.getDealId();
//                int buyQuantity = userBuyInfoMapper.selectByKey(id);
//                dealPriceQuantity.setQuantity(buyQuantity - dealPriceQuantity.getQuantity());
//            }else if(dealPriceQuantity.getStatus() == StockStatusEnum.SalePartSuccess){
//                long id = dealPriceQuantity.getDealId();
//                int saleQuantity = userSaleInfoMapper.selectByKey(id);
//                dealPriceQuantity.setQuantity(saleQuantity - dealPriceQuantity.getQuantity());
//            }
//        }
        return dealPriceQuantityList;
    }

    @Override
    public List<Long> selectStockNumber() {
        return stockInfoMapper.selectStockNumber();
    }
}
