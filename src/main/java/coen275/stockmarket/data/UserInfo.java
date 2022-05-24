package coen275.stockmarket.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Long Id;
    private String userName;
    private String password;
    private Double cash;
    private List<UserStocksInfo> userStocksInfoList;

}
