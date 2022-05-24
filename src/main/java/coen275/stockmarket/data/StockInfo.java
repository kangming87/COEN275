package coen275.stockmarket.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockInfo {
    Long stockId;
    Long stockCode;
    String stockName;


}
