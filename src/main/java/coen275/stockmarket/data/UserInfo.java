package coen275.stockmarket.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    Long Id;
    Long userName;
    Double cash;
    List<UserStocksInfo> userStocksInfoList;
}
