package coen275.stockmarket.Mapper;

import coen275.stockmarket.data.UserStocksInfo;
import org.apache.ibatis.annotations.Insert;

public interface DealMapper {
    @Insert({

    })
    int insertDealInfo(UserStocksInfo userStocksInfo);
}
