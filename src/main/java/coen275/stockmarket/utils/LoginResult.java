package coen275.stockmarket.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {

    //返回信息
    private String result;

    //数据是否正常请求
    private boolean success;




}
