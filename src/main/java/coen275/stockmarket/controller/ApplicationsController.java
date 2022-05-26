package coen275.stockmarket.controller;

import coen275.stockmarket.Exception.CommonException;
import coen275.stockmarket.Service.BuyService;
import coen275.stockmarket.Service.SaleService;
import coen275.stockmarket.Service.StockService;
import coen275.stockmarket.Service.UserInfoService;
import coen275.stockmarket.data.*;
import coen275.stockmarket.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationsController {

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
                                  @PathVariable("quantity") Integer quantity, @PathVariable("buyPrice") Double buyPrice) {
        StockInfo stockInfo = stockService.getStockInfo(stockId);
        UserBuyInfo userBuyInfo = new UserBuyInfo();
        userBuyInfo.setUserId(userId);
        userBuyInfo.setStockId(stockId);
        userBuyInfo.setStockCode(stockInfo.getStockCode());
        userBuyInfo.setStockName(stockInfo.getStockName());
        userBuyInfo.setQuantity(quantity);
        userBuyInfo.setBuyPrice(buyPrice);
        if(!buyService.buyStock(userBuyInfo)){
            throw new CommonException("当前现金没办法买这个价格数量的股票", "NoEnoughMoney", 409);
        }
        return new SuccessResponse(200,"success","提交成功!");
    }

    @PostMapping("/saleStock")
    public SuccessResponse saleStock(@PathVariable("userId") Long userId, @PathVariable("stockId") Long stockId,
                                    @PathVariable("quantity") Integer quantity, @PathVariable("salePrice") Double salePrice) {
        StockInfo stockInfo = stockService.getStockInfo(stockId);
        UserSaleInfo userSaleInfo = new UserSaleInfo();
        userSaleInfo.setUserId(userId);
        userSaleInfo.setStockId(stockId);
        userSaleInfo.setStockCode(stockInfo.getStockCode());
        userSaleInfo.setStockName(stockInfo.getStockName());
        userSaleInfo.setQuantity(quantity);
        userSaleInfo.setSalePrice(salePrice);
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
