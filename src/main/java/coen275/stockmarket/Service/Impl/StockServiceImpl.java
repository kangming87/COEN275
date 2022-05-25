package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Mapper.DealMapper;
import coen275.stockmarket.Mapper.StockDetailMapper;
import coen275.stockmarket.Mapper.StockMapper;
import coen275.stockmarket.Service.StockService;
import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.StockInfo;
import coen275.stockmarket.data.StockInfoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired(required = false)
    StockMapper stockMapper;

    @Autowired(required = false)
    StockDetailMapper stockDetailMapper;

    @Autowired(required = false)
    DealMapper dealMapper;

    @Override
    public StockInfo getStockInfo(Long stockId) {
        StockInfo stockInfo = stockMapper.getStockInfo(stockId);
        return stockInfo;
    }

    @Override
    public List<StockInfo> getStockInfoList() {
        return stockMapper.getStockInfoList();
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
