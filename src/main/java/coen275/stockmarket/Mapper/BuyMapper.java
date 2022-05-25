package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("buyMapper")
public interface BuyMapper {

    @Select({
            "select * from userBuyInfo "
    })
    List<UserStocksInfo> getUserBuyStockInfo(); //选择status为1代表买未成交

    @Update({

    })
    void updateUserBuyStockInfo(DealPriceQuantity dealPriceQuantity);

    @Insert({
            "insert into  userBuyInfo (userId ,stockId, stockCode, stockName, quantity, buyPrice)",
            "values (#{userId, jdbcType=BIGINT}, #{stockId, jdbcType=BIGINT}, #{stockCode, jdbcType=BIGINT},",
            "#{stockName, jdbcType=VARCHAR}, #{quantity, jdbcType=BIGINT}, #{buyPrice, jdbcType=DOUBLE})"
    })
    void insertUserBuy(UserBuyInfo userBuyInfo);
}
