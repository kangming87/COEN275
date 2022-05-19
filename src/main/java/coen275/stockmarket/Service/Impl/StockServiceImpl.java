package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Mapper.StockMapper;
import coen275.stockmarket.Service.StockService;
import coen275.stockmarket.data.StockInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class StockServiceImpl implements StockService {
    @Autowired
    StockMapper stockMapper;

    @Override
    public StockInfo getStockInfo(Long stockId) {
        StockInfo stockInfo = stockMapper.getStockInfo(stockId);
        return stockInfo;
    }
}
