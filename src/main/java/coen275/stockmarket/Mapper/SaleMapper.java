package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.data.UserSaleInfo;
import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SaleMapper {

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
