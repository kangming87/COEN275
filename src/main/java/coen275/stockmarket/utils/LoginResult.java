package coen275.stockmarket.utils;

public class LoginResult {

    //返回信息
    private String msg;

    //数据是否正常请求
    private boolean success;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
