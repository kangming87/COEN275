package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.StockInfoDetail;
import coen275.stockmarket.data.StockInfoDetailExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Component("stockInfoDetailMapper")
public interface StockInfoDetailMapper {
    @Delete({
        "delete from stock_info_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into stock_info_detail (id, stockId, ",
        "stockCode, stockName, ",
        "start_price, curr_price, ",
        "max_price, min_price)",
        "values (#{id,jdbcType=BIGINT}, #{stockId,jdbcType=BIGINT}, ",
        "#{stockCode,jdbcType=BIGINT}, #{stockName,jdbcType=VARCHAR}, ",
        "#{startPrice,jdbcType=DECIMAL}, #{currPrice,jdbcType=DECIMAL}, ",
        "#{maxPrice,jdbcType=DECIMAL}, #{minPrice,jdbcType=DECIMAL})"
    })
    int insert(StockInfoDetail record);

    int insertSelective(StockInfoDetail record);

    List<StockInfoDetail> selectByExample(StockInfoDetailExample example);

    @Select({
        "select",
        "id, stockId, stockCode, stockName, start_price, curr_price, max_price, min_price",
        "from stock_info_detail",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    StockInfoDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockInfoDetail record);

    @Update({
        "update stock_info_detail",
        "set stockId = #{stockId,jdbcType=BIGINT},",
          "stockCode = #{stockCode,jdbcType=BIGINT},",
          "stockName = #{stockName,jdbcType=VARCHAR},",
          "start_price = #{startPrice,jdbcType=DECIMAL},",
          "curr_price = #{currPrice,jdbcType=DECIMAL},",
          "max_price = #{maxPrice,jdbcType=DECIMAL},",
          "min_price = #{minPrice,jdbcType=DECIMAL}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StockInfoDetail record);


    @Update(value = "update stock_info_detail set curr_price=#{currPrice}, max_price=#{maxPrice}, min_price=#{minPrice} where stockId=#{stockId}")
    void updateStockInfo(Long stockId, Double currPrice, Double maxPrice, Double minPrice);

    @Select({
            "select",
            "id, stockId, stockCode, stockName, start_price, curr_price, max_price, min_price",
            "from stock_info_detail",
            "where stockId = #{stockId,jdbcType=BIGINT}"
    })
    StockInfoDetail getStockInfoDetail(@Param("stockId") Long stockId);
}