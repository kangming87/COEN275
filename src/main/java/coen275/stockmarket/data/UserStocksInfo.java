package coen275.stockmarket.data;

import coen275.stockmarket.Enum.StockStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStocksInfo {
    Long stockId;
    Long stockCode;
    String stockName;
    Timestamp time;
    Long quantity;
    StockStatusEnum status; //买卖状态 0代表买已成交，1代表买未成交，2代表 卖成交，3代表卖未成交
    Double price;

}
