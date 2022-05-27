package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.UserStocksInfo;
import coen275.stockmarket.data.UserStocksInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Mapper
@Component("userStocksInfoMapper")
public interface UserStocksInfoMapper {
    @Delete({
        "delete from user_stocks_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_stocks_info (id, ",
        "userId, stockId, stockCode, ",
        "stockName,",
        "quantity, status, price)",
        "values (#{id,jdbcType=BIGINT}, #{#{userId,jdbcType=BIGINT}, #{stockId,jdbcType=BIGINT}, #{stockCode,jdbcType=BIGINT}, ",
        "#{stockName,jdbcType=VARCHAR},",
        "#{quantity,jdbcType=INTEGER}, #{status,jdbcType=CHAR}, #{price,jdbcType=DECIMAL})"
    })
    int insert(UserStocksInfo record);

    int insertSelective(UserStocksInfo record);

    List<UserStocksInfo> selectByExample(UserStocksInfoExample example);

    @Select({
        "select",
        "id, dealId, userId, stockId, stockCode, stockName, time, quantity, status, price",
        "from user_stocks_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserStocksInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserStocksInfo record);

    @Update({
        "update user_stocks_info",
        "set dealId = #{dealId,jdbcType=BIGINT},",
          "userId = #{userId,jdbcType=BIGINT},",
          "stockId = #{stockId,jdbcType=BIGINT},",
          "stockCode = #{stockCode,jdbcType=BIGINT},",
          "stockName = #{stockName,jdbcType=VARCHAR},",
          "time = #{time,jdbcType=TIMESTAMP},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "status = #{status,jdbcType=CHAR},",
          "price = #{price,jdbcType=DECIMAL}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserStocksInfo record);
}