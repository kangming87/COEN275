package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.data.UserInfo;
import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BuyMapper {

    @Select({

    })
    List<UserStocksInfo> getUserBuyStockInfo(); //选择status为1代表买未成交

    @Update({

    })
    void updateUserBuyStockInfo(DealPriceQuantity dealPriceQuantity);

    @Insert({

    })
    void insertUserBuy(UserBuyInfo userBuyInfo);
}
