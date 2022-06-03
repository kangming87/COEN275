package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.data.UserBuyInfoExample;
import java.util.List;

import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Mapper
@Component("userBuyInfoMapper")
public interface UserBuyInfoMapper {
    @Delete({
        "delete from user_buy_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_buy_info (id, userId, ",
        "stockId, stockCode, ",
        "stockName, quantity, ",
        "buyPrice)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{stockId,jdbcType=BIGINT}, #{stockCode,jdbcType=BIGINT}, ",
        "#{stockName,jdbcType=VARCHAR}, #{quantity,jdbcType=INTEGER}, ",
        "#{buyPrice,jdbcType=DECIMAL})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(UserBuyInfo record);


    int insertSelective(UserBuyInfo record);

    List<UserBuyInfo> selectByExample(UserBuyInfoExample example);

    @Select({
        "select",
        "id, userId, stockId, stockCode, stockName, quantity, buyPrice",
        "from user_buy_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserBuyInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBuyInfo record);

    @Update({
        "update user_buy_info",
        "set userId = #{userId,jdbcType=BIGINT},",
          "stockId = #{stockId,jdbcType=BIGINT},",
          "stockCode = #{stockCode,jdbcType=BIGINT},",
          "stockName = #{stockName,jdbcType=VARCHAR},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "buyPrice = #{buyPrice,jdbcType=DECIMAL}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserBuyInfo record);


    @Select({
            "select * from user_buy_info "
    })
    List<UserStocksInfo> getUserBuyStockInfo(); //选择status为1代表买未成交

    @Update({

    })
    void updateUserBuyStockInfo(DealPriceQuantity dealPriceQuantity);

    @Insert({
            "insert into  user_buy_info (userId ,stockId, stockCode, stockName, quantity, buyPrice)",
            "values (#{userId, jdbcType=BIGINT}, #{stockId, jdbcType=BIGINT}, #{stockCode, jdbcType=BIGINT},",
            "#{stockName, jdbcType=VARCHAR}, #{quantity, jdbcType=BIGINT}, #{buyPrice, jdbcType=DOUBLE})"
    })
    void insertUserBuy(UserBuyInfo userBuyInfo);


    @Select({
            "select quantity from user_buy_info where id = #{id,jdbcType=BIGINT}"
    })
    int selectByKey(Long id);

    @Select({
            "select",
            "id, userId, stockId, stockCode, stockName, quantity, buyPrice",
            "from user_buy_info",
            "where id = #{id,jdbcType=BIGINT}"
    })
    UserBuyInfo selectByDealId(Long id);

}