package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DealMapper {
    @Insert({
            "insert into  dealprice (dealId, userId ,stockId,price, quantity, status)",
            "values (#{dealId, jdbcType=BIGINT}, #{userId, jdbcType=BIGINT}, #{stockId, jdbcType=BIGINT}, ",
            "#{price, jdbcType=DOUBLE}, #{quantity, jdbcType=BIGINT}, #{status, jdbcType=TINYINT})"
    })
    int insertDealInfo(UserStocksInfo userStocksInfo);

    @Update({
            "update dealprice",
            "set userId = #{userId, jdbcType=BIGINT},",
            "stockId = #{stockId, jdbcType=BIGINT},",
            "price = #{price, jdbcType=DOUBLE},",
            "quantity = #{quantity, jdbcType=BIGINT},",
            "status = #{status, jdbcType=TINYINT} where dealId = #{dealId, jdbcType=BIGINT}"
    })
    void updateUserDealInfo(DealPriceQuantity dealPriceQuantity);

    @Select({
            "select dealId, userId,  stockId, price, quantity, status from dealprice where dealId = #{dealId,jdbcType=BIGINT}"
    })
    List<DealPriceQuantity> getStockTradeList(Long stockId);
}
