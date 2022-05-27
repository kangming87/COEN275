package coen275.stockmarket.utils;

import coen275.stockmarket.data.StockInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class stock_and_profit {
    private Long stockId;

    private Long stockCode;

    private String stockName;
    private Double profit;
}
