package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.StockInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component("stockMapper")
public interface StockMapper {
    @Select(value = "select s.stockId,s.stockCode,s.stockName from stockinfo s where s.stockId=#{stockId}")
    @Results({
            @Result(property = "stockId",column = "stockId"),
            @Result(property = "stockCode", column = "stockCode"),
            @Result(property = "stockName", column = "stockName")
    })
    StockInfo getStockInfo(@Param("stockId") Long stockId);


    @Select(value = "select * from stockinfo")
    @Results({
            @Result(property = "stockId",column = "stockId"),
            @Result(property = "stockCode", column = "stockCode"),
            @Result(property = "stockName", column = "stockName")
    })
    List<StockInfo> getStockInfoList();
}
