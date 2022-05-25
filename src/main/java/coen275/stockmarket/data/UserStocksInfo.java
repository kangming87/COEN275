package coen275.stockmarket.data;

import coen275.stockmarket.Enum.StockStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStocksInfo {
    private Long id;

    private Long dealId;

    private Long userId;

    private Long stockId;

    private Long stockCode;

    private String stockName;

    private Timestamp time;

    private Integer quantity;

    private StockStatusEnum status;

    private Double price;


}