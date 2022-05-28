package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.StockInfo;
import coen275.stockmarket.data.StockInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Mapper
@Component("stockInfoMapper")
public interface StockInfoMapper {
    @Delete({
        "delete from stock_info",
        "where stockId = #{stockId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long stockId);

    @Insert({
        "insert into stock_info (stockId, stockCode, ",
        "stockName)",
        "values (#{stockId,jdbcType=BIGINT}, #{stockCode,jdbcType=BIGINT}, ",
        "#{stockName,jdbcType=VARCHAR})"
    })
    int insert(StockInfo record);

    int insertSelective(StockInfo record);

    List<StockInfo> selectByExample(StockInfoExample example);

    @Select({
        "select",
        "stockId, stockCode, stockName",
        "from stock_info",
        "where stockId = #{stockId,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    StockInfo selectByPrimaryKey(Long stockId);

    int updateByPrimaryKeySelective(StockInfo record);

    @Update({
        "update stock_info",
        "set stockCode = #{stockCode,jdbcType=BIGINT},",
          "stockName = #{stockName,jdbcType=VARCHAR}",
        "where stockId = #{stockId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StockInfo record);

    @Select({
            "SELECT stockId FROM stock_info"
    })
    List<Long> selectStockNumber();
}