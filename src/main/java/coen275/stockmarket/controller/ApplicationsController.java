package coen275.stockmarket.controller;

import coen275.stockmarket.Exception.CommonException;
import coen275.stockmarket.Service.*;
import coen275.stockmarket.data.*;
import coen275.stockmarket.utils.SuccessResponse;
import coen275.stockmarket.utils.stock_and_profit;
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

    @Autowired
    DealMatchService dealMatchService;


    @PostMapping("/buyStock/{userId}/{stockId}/{quantity}/{buyPrice}")
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

    @PostMapping("/saleStock/{userId}/{stockId}/{quantity}/{salePrice}")
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

    @GetMapping("/getStockDetail/{stockId}")
    public StockInfoDetail getStockInfoDetail(@PathVariable("stockId") Long stockId){
        return stockService.getStockDetailInfo(stockId);
    }

    @GetMapping("/getStockTradeList/{stockId}")
    public List<DealPriceQuantity> getStockTradeList(@PathVariable("stockId") Long stockId){
        return stockService.getStockTradeList(stockId);
    }

    @GetMapping("/getUserTradeList/{userId}")
    public List<DealPriceQuantity> getUserTradeList(@PathVariable("userId") Long userId){
        return stockService.getUserTradeList(userId);
    }


    @GetMapping("/dealMatch")
    public SuccessResponse dealMatch(){
        dealMatchService.updateDealQueue();
        return new SuccessResponse(200,"success","提交成功!");
    }

    @GetMapping("/getSuggestionStocks")
    public List<stock_and_profit> getSuggestionStocks(){
        return stockService.getSuggestionStocks();

    }
}
