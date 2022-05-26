package coen275.stockmarket.Service;

import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.utils.SuccessResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface BuyService {

    Boolean buyStock(UserBuyInfo userBuyInfo);
}
