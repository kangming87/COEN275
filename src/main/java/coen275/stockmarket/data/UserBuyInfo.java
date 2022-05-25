package coen275.stockmarket.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBuyInfo {
    private Long id;

    private Long userId;

    private Long stockId;

    private Long stockCode;

    private String stockName;

    private Integer quantity;

    private Double buyPrice;

}