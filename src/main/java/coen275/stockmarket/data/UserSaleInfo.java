package coen275.stockmarket.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaleInfo {
    Long id;
    Long userId;
    Long stockId;
    Long stockCode;
    String stockName;
    Long quantity;
    Double salePrice;
}
