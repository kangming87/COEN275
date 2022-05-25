package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.DealPriceQuantityExample;
import java.util.List;

import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Mapper
@Component("dealPriceQuantityMapper")
public interface DealPriceQuantityMapper {
    @Delete({
        "delete from deal_price_quantity",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into deal_price_quantity (id, dealId, ",
        "userId, stockId, price, ",
        "quantity, status)",
        "values (#{id,jdbcType=BIGINT}, #{dealId,jdbcType=BIGINT}, ",
        "#{userId,jdbcType=BIGINT}, #{stockId,jdbcType=BIGINT}, #{price,jdbcType=DECIMAL}, ",
        "#{quantity,jdbcType=INTEGER}, #{status,jdbcType=CHAR})"
    })
    int insert(DealPriceQuantity record);

    int insertSelective(DealPriceQuantity record);

    List<DealPriceQuantity> selectByExample(DealPriceQuantityExample example);

    @Select({
        "select",
        "id, dealId, userId, stockId, price, quantity, status",
        "from deal_price_quantity",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    DealPriceQuantity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DealPriceQuantity record);

    @Update({
        "update deal_price_quantity",
        "set dealId = #{dealId,jdbcType=BIGINT},",
          "userId = #{userId,jdbcType=BIGINT},",
          "stockId = #{stockId,jdbcType=BIGINT},",
          "price = #{price,jdbcType=DECIMAL},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "status = #{status,jdbcType=CHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DealPriceQuantity record);


    @Insert({
            "insert into  deal_price_quantity (dealId, userId ,stockId,price, quantity, status)",
            "values (#{dealId, jdbcType=BIGINT}, #{userId, jdbcType=BIGINT}, #{stockId, jdbcType=BIGINT}, ",
            "#{price, jdbcType=DOUBLE}, #{quantity, jdbcType=BIGINT}, #{status, jdbcType=TINYINT})"
    })
    int insertDealInfo(UserStocksInfo userStocksInfo);

    @Update({
            "update deal_price_quantity",
            "set userId = #{userId, jdbcType=BIGINT},",
            "stockId = #{stockId, jdbcType=BIGINT},",
            "price = #{price, jdbcType=DOUBLE},",
            "quantity = #{quantity, jdbcType=BIGINT},",
            "status = #{status, jdbcType=TINYINT} where dealId = #{dealId, jdbcType=BIGINT}"
    })
    void updateUserDealInfo(DealPriceQuantity dealPriceQuantity);

    @Select({
            "select dealId, userId,  stockId, price, quantity, status from deal_price_quantity where stockId = #{stockId,jdbcType=BIGINT}"
    })
    List<DealPriceQuantity> getStockTradeList(Long stockId);

    @Select({
            "select dealId, userId,  stockId, price, quantity, status from deal_price_quantity where stockId = #{stockId,jdbcType=BIGINT} && userId = #{stockId,jdbcType=BIGINT}"
    })
    List<DealPriceQuantity> getUserStockList(Long stockId, Long userId);
}