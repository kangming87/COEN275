package coen275.stockmarket.data;

import coen275.stockmarket.Enum.StockStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserSaleInfo {
    Long userId;
    Long stockId;
    Long stockCode;
    String stockName;
    Long quantity;
    Double salePrice;
}
