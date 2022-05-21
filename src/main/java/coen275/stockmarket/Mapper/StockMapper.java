package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.StockInfo;
import org.apache.ibatis.annotations.*;

public interface StockMapper {
    @Select({

    })
    StockInfo getStockInfo(Long stockId);

    @Update({

    })
    void updateStockInfo(Long stockId, Double minutePrice);
}
