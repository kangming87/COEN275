package coen275.stockmarket.controller;

import coen275.stockmarket.Exception.CommonException;
import coen275.stockmarket.Service.BuyService;
import coen275.stockmarket.Service.SaleService;
import coen275.stockmarket.Service.StockService;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.*;
import coen275.stockmarket.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationsController extends BaseController{

    @Autowired
    BuyService buyService;

    @Autowired
    StockService stockService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    SaleService saleService;

    @PostMapping("/buyStock")
    public SuccessResponse buyStock(@PathVariable("userId") Long userId, @PathVariable("stockId") Long stockId,
                                  @PathVariable("quantity") Long quantity, @PathVariable("buyPrice") Double buyPrice) {
        long uid = getUserId();
        if (uid < 0) {
            throw new CommonException("登录状态已失效，请重新登录！", "user_login_status_error", 407);
        }
        StockInfo stockInfo = stockService.getStockInfo(stockId);
        UserBuyInfo userBuyInfo = new UserBuyInfo(userId, stockId ,stockInfo.getStockCode(), stockInfo.getStockName(), quantity, buyPrice);
        if(!buyService.buyStock(userBuyInfo)){
            throw new CommonException("当前现金没办法买这个价格数量的股票", "NoEnoughMoney", 409);
        }
        return new SuccessResponse(200,"success","提交成功!");
    }

    @PostMapping("/saleStock")
    public SuccessResponse saleStock(@PathVariable("userId") Long userId, @PathVariable("stockId") Long stockId,
                                    @PathVariable("quantity") Long quantity, @PathVariable("salePrice") Double salePrice) {
        long uid = getUserId();
        if (uid < 0) {
            throw new CommonException("登录状态已失效，请重新登录！", "user_login_status_error", 407);
        }
        StockInfo stockInfo = stockService.getStockInfo(stockId);
        UserSaleInfo userSaleInfo = new UserSaleInfo(userId, stockId ,stockInfo.getStockCode(), stockInfo.getStockName(), quantity, salePrice);
        saleService.saleStock(userSaleInfo);
        return new SuccessResponse(200,"success","提交成功!");
    }

    @GetMapping("/getStockList")
    public List<StockInfo> getStockInfoList(){
        return stockService.getStockInfoList();
    }

    @GetMapping("/getStockDetail")
    public StockInfoDetail getStockInfoDetail(@PathVariable("stockId") Long stockId){
        return stockService.getStockDetailInfo(stockId);
    }

    @GetMapping("/getStockTradeList")
    public List<DealPriceQuantity> getStockTradeList(@PathVariable("stockId") Long stockId){
        return stockService.getStockTradeList(stockId);
    }


}
