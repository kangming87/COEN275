package coen275.stockmarket.Service;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.StockInfo;
import coen275.stockmarket.data.StockInfoDetail;

import java.util.List;

public interface StockService {

    StockInfo getStockInfo(Long stockId);

    List<StockInfo> getStockInfoList();

    StockInfoDetail getStockDetailInfo(Long stockId);

    List<DealPriceQuantity> getStockTradeList(Long stockId);
}
