package coen275.stockmarket.data;

import coen275.stockmarket.Enum.StockStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealPriceQuantity {
    private Long id;

    private Long dealId;

    private Long userId;

    private Long stockId;

    private Double price;

    private Integer quantity;

    private StockStatusEnum status;

}