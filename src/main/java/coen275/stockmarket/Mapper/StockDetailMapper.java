package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.StockInfoDetail;
import org.apache.ibatis.annotations.*;

public interface StockDetailMapper {

    @Update(value = "update stockinfo_detail set curr_price=#{curr_price}, max_price=#{max_price}, min_price=#{min_price}, where stockId=#{stockId}")
    void updateStockInfo(Long stockId, Double curr_price, Double max_price, Double min_price);

    @Select(value = "select * from stockinfo_detail s where s.stockId=#{stockId}")
    @Results({
            @Result(property = "stockId",column = "stockId"),
            @Result(property = "stockCode", column = "stockCode"),
            @Result(property = "stockName", column = "stockName"),
            @Result(property = "start_price", column = "start_price"),
            @Result(property = "curr_price", column = "curr_price"),
            @Result(property = "max_price", column = "max_price"),
            @Result(property = "min_price", column = "min_price")
    })
    StockInfoDetail getStockInfoDetail(@Param("stockId") Long stockId);
}
