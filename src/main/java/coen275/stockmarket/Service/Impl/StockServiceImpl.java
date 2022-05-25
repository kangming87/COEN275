package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Mapper.DealPriceQuantityMapper;
import coen275.stockmarket.Mapper.StockInfoDetailMapper;
import coen275.stockmarket.Mapper.StockInfoMapper;
import coen275.stockmarket.Service.StockService;
import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.StockInfo;
import coen275.stockmarket.data.StockInfoDetail;
import coen275.stockmarket.data.StockInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    StockInfoMapper stockInfoMapper;

    @Autowired
    StockInfoDetailMapper stockDetailMapper;

    @Autowired
    DealPriceQuantityMapper dealMapper;

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
    public StockInfoDetail getStockDetailInfo(Long stockId) {
        return stockDetailMapper.getStockInfoDetail(stockId);
    }

    @Override
    public List<DealPriceQuantity> getStockTradeList(Long stockId) {
        return dealMapper.getStockTradeList(stockId);
    }
}
