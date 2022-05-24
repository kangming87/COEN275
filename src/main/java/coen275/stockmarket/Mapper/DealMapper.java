package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DealMapper {
    @Insert({

    })
    int insertDealInfo(UserStocksInfo userStocksInfo);

    @Update({

    })
    void updateUserDealInfo(DealPriceQuantity dealPriceQuantity);

    @Select({

    })
    List<DealPriceQuantity> getStockTradeList(Long stockId);
}
