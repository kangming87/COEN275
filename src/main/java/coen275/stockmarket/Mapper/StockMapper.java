package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.StockInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StockMapper {
    @Select({

    })
    StockInfo getStockInfo(Long stockId);


    @Select({

    })
    List<StockInfo> getStockInfoList();
}
