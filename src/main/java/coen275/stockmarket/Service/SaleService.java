package coen275.stockmarket.Service;


import coen275.stockmarket.data.UserSaleInfo;
import org.springframework.stereotype.Service;

@Service
public interface SaleService {
    Boolean saleStock(UserSaleInfo userSaleInfoInfo);
}
