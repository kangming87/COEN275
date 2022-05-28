package coen275.stockmarket.Service;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.StockInfo;
import coen275.stockmarket.data.StockInfoDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {

    StockInfo getStockInfo(Long stockId);

    List<StockInfo> getStockInfoList();

    StockInfoDetail getStockDetailInfo(Long stockId);

    List<DealPriceQuantity> getStockTradeList(Long stockId);

    List<DealPriceQuantity> getUserTradeList(Long userId);

    List<Long> selectStockNumber();
}
