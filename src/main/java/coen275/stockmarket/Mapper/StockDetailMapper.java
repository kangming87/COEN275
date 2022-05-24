package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.StockInfoDetail;
import org.apache.ibatis.annotations.*;

public interface StockDetailMapper {

    @Update({

    })
    void updateStockInfo(Long stockId, Double minutePrice, Double maxPrice, Double minPrice);

    @Select({

    })
    StockInfoDetail getStockInfoDetail(Long stockId);
}
