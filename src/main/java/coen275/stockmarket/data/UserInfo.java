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

    public Long getId(){
        return Id;
    }

    public void setId(Long id){
        this.Id = id;
    }

    public String getUsername(){
        return userName;
    }

    public void setUsername(String userName){
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash){
        this.cash = cash;
    }

    public List<UserStocksInfo> getUserStocksInfoList(){
        return userStocksInfoList;
    }
}
