package coen275.stockmarket.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInfoDetail {
    Long stockId;
    Long stockCode;
    String stockName;
    Double startPrice;
    Double currPrice;
    Double maxPrice;
    Double minPrice;

}
