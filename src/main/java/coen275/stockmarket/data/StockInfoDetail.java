package coen275.stockmarket.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInfoDetail {
    private Long id;

    private Long stockId;

    private Long stockCode;

    private String stockName;

    private Double startPrice;

    private Double currPrice;

    private Double maxPrice;

    private Double minPrice;
}