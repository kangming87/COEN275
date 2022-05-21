package coen275.stockmarket.data;

import coen275.stockmarket.Enum.StockStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealPriceQuantity {
    Long id;
    Long userId;
    Double price;
    Long quantity;
    StockStatusEnum status; //买卖状态 0代表买已成交，1代表买未成交，2代表买成交部分， 3代表 卖成交，4代表卖未成交， 5代表卖成交部分
}