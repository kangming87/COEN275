package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserSaleInfo;
import coen275.stockmarket.data.UserSaleInfoExample;
import java.util.List;

import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Mapper
@Component("userSaleInfoMapper")
public interface UserSaleInfoMapper {
    @Delete({
        "delete from user_sale_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_sale_info (id, userId, ",
        "stockId, stockCode, ",
        "stockName, quantity, ",
        "salePrice)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{stockId,jdbcType=BIGINT}, #{stockCode,jdbcType=BIGINT}, ",
        "#{stockName,jdbcType=VARCHAR}, #{quantity,jdbcType=INTEGER}, ",
        "#{salePrice,jdbcType=DECIMAL})"
    })
    int insert(UserSaleInfo record);

    int insertSelective(UserSaleInfo record);

    List<UserSaleInfo> selectByExample(UserSaleInfoExample example);

    @Select({
        "select",
        "id, userId, stockId, stockCode, stockName, quantity, salePrice",
        "from user_sale_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserSaleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSaleInfo record);

    @Update({
        "update user_sale_info",
        "set userId = #{userId,jdbcType=BIGINT},",
          "stockId = #{stockId,jdbcType=BIGINT},",
          "stockCode = #{stockCode,jdbcType=BIGINT},",
          "stockName = #{stockName,jdbcType=VARCHAR},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "salePrice = #{salePrice,jdbcType=DECIMAL}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserSaleInfo record);



    @Select({

    })
    List<UserStocksInfo> getUserSaleStockInfo(); //选择status为3代表买未成交


    @Update({

    })
    void updateUserSaleStockInfo(DealPriceQuantity dealPriceQuantity);

    @Insert({

    })
    void insertUserSale(UserSaleInfo userSaleInfo);
}