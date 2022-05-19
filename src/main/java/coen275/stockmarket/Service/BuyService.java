package coen275.stockmarket.Service;

import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.utils.SuccessResponse;

public interface BuyService {

    Boolean buyStock(UserBuyInfo userBuyInfo);
}
