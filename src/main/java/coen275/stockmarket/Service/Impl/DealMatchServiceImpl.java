package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Mapper.*;
import coen275.stockmarket.Service.BuyService;
import coen275.stockmarket.Service.DealMatchService;
import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserStocksInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class DealMatchServiceImpl implements DealMatchService {

    @Autowired
    UserSaleInfoMapper saleMapper;

    @Autowired
    UserBuyInfoMapper buyMapper;

    @Autowired
    StockInfoMapper stockInfoMapper;

    @Autowired
    BuyService buyService;

    @Autowired
    DealPriceQuantityMapper dealMapper;

    @Autowired
    StockInfoDetailMapper stockDetailMapper;


    Double minutePrice;


    Long stockId;



    @Override
    public Boolean updateDealQueue() {
        PriorityQueue<UserStocksInfo> salePriorityQueue = new PriorityQueue<>(new Comparator<UserStocksInfo>() {
            @Override
            public int compare(UserStocksInfo o1, UserStocksInfo o2) {
                if(o1.getPrice() != o2.getPrice()){
                    return (int) (o1.getPrice() - o2.getPrice());
                }else if(o1.getQuantity() != o2.getQuantity()){
                    return (int) (o2.getQuantity() - o1.getQuantity());
                }else{
                    if(o1.getTime().compareTo(o2.getTime()) < 0){
                        return -1;
                    }
                    return 1;
                }
            }
        });

        PriorityQueue<UserStocksInfo> buyPriorityQueue = new PriorityQueue<>(new Comparator<UserStocksInfo>() {
            @Override
            public int compare(UserStocksInfo o1, UserStocksInfo o2) {
                if(o1.getPrice() != o2.getPrice()){
                    return (int) (o2.getPrice() - o1.getPrice());
                }else if(o1.getQuantity() != o2.getQuantity()){
                    return (int) (o2.getQuantity() - o1.getQuantity());
                }else{
                    if(o1.getTime().compareTo(o2.getTime()) < 0){
                        return -1;
                    }
                    return 1;
                }
            }
        });


        List<UserStocksInfo> saleList = saleMapper.getUserSaleStockInfo();

        List<UserStocksInfo> buyList = buyMapper.getUserBuyStockInfo();

        Map<Long, DealPriceQuantity> userBuyInfoMap = new HashMap<>();

        Map<Long, DealPriceQuantity> userSaleInfoMap = new HashMap<>();

        List<DealPriceQuantity> dealPriceQuantitylist = new ArrayList<>();

        Double maxPrice = 0.0;

        Double minPrice = 0.0;


        for(UserStocksInfo userStocksInfo : saleList){
            salePriorityQueue.add(userStocksInfo);
        }
        for(UserStocksInfo userStocksInfo : buyList){
            buyPriorityQueue.add(userStocksInfo);
        }
        while (buyPriorityQueue.size() != 0 && salePriorityQueue.size() != 0){
            UserStocksInfo maxBuy = buyPriorityQueue.poll();
            UserStocksInfo minSale = salePriorityQueue.poll();
            stockId = maxBuy.getStockId();


            while(maxBuy.getQuantity() != 0 || minSale.getQuantity() != 0){
                double eachPrice = 0.0;
                int eachQuantity = 0;
                if(maxBuy.getQuantity() == 0){
                    if(buyPriorityQueue.size() != 0){
                        maxBuy = buyPriorityQueue.poll();
                        if(maxBuy.getQuantity() > minSale.getQuantity()){

                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = minSale.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuyPartSuccess));
                            maxBuy.setQuantity(maxBuy.getQuantity() - minSale.getQuantity()) ;
                            minSale.setQuantity(0);

                        }else if(maxBuy.getQuantity() < minSale.getQuantity()){
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;

                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SalePartSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                            minSale.setQuantity(minSale.getQuantity() - maxBuy.getQuantity());
                            maxBuy.setQuantity(0);
                        }else{
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                            break;
                        }
                    }else{
                        break;
                    }
                }else if(minSale.getQuantity() == 0){
                    if(salePriorityQueue.size() != 0){
                        minSale = salePriorityQueue.poll();
                        if(maxBuy.getQuantity() > minSale.getQuantity()){
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = minSale.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuyPartSuccess));
                            maxBuy.setQuantity(maxBuy.getQuantity() - minSale.getQuantity()) ;
                            minSale.setQuantity(0);

                        }else if(maxBuy.getQuantity() < minSale.getQuantity()){
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SalePartSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                            minSale.setQuantity(minSale.getQuantity() - maxBuy.getQuantity());
                            maxBuy.setQuantity(0);

                        }else{
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                            break;
                        }
                    }else{
                        break;
                    }
                }else{
                    if(maxBuy.getQuantity() > minSale.getQuantity()){
                        eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                        eachQuantity = minSale.getQuantity() ;
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SalePartSuccess));
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                        maxBuy.setQuantity(maxBuy.getQuantity() - minSale.getQuantity()) ;
                        minSale.setQuantity(0);

                    }else if(maxBuy.getQuantity() < minSale.getQuantity()){
                        eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                        eachQuantity = maxBuy.getQuantity() ;
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SalePartSuccess));
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                        int newQuantity = minSale.getQuantity() - maxBuy.getQuantity();
                        maxBuy.setQuantity(0);
                        minSale.setQuantity(newQuantity);
                    }else{
                        eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                        eachQuantity = maxBuy.getQuantity() * 2;
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null,minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, minSale.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                        break;
                    }
                }
                if(maxPrice == 0.0){
                    maxPrice = eachPrice;
                }else {
                    maxPrice = Math.max(eachPrice, maxPrice);
                }

                if(minPrice == 0.0){
                    minPrice = eachPrice;
                }else {
                    minPrice = Math.min(eachPrice, minPrice);
                }
            }
        }
        Double totalPrice = 0.0;
        Long totalQuantity = 0L;
        for(DealPriceQuantity dealPriceQuantity : dealPriceQuantitylist){
            totalPrice += dealPriceQuantity.getPrice() * dealPriceQuantity.getQuantity();
            totalQuantity += dealPriceQuantity.getQuantity();
        }
        minutePrice = totalPrice / totalQuantity;

        stockDetailMapper.updateStockInfo(stockId, minutePrice, maxPrice, minPrice);//更改股票具体信息

        for(DealPriceQuantity dealPriceQuantity : dealPriceQuantitylist){
            if(dealPriceQuantity.getStatus() == StockStatusEnum.BuyPartSuccess
                    || dealPriceQuantity.getStatus() == StockStatusEnum.BuySuccess){
                if(userBuyInfoMap.containsKey(dealPriceQuantity.getUserId())){
                    if((userBuyInfoMap.get(dealPriceQuantity.getUserId()).getStatus() ==StockStatusEnum.BuyPartSuccess
                            && dealPriceQuantity.getStatus() == StockStatusEnum.BuySuccess) || (userBuyInfoMap.get(dealPriceQuantity.getUserId()).getStatus() ==StockStatusEnum.BuySuccess
                            && dealPriceQuantity.getStatus() == StockStatusEnum.BuyPartSuccess)){
                        DealPriceQuantity dealBuyPriceQuantity = userBuyInfoMap.get(dealPriceQuantity.getUserId());
                        dealBuyPriceQuantity.setStatus(StockStatusEnum.BuySuccess);
                        Double currPrice = (dealPriceQuantity.getQuantity() * dealPriceQuantity.getPrice()
                                + dealBuyPriceQuantity.getQuantity() * dealBuyPriceQuantity.getPrice())
                                / (dealPriceQuantity.getQuantity() + dealBuyPriceQuantity.getQuantity());
                        dealBuyPriceQuantity.setPrice(currPrice);
                        userBuyInfoMap.put(dealPriceQuantity.getUserId(), dealBuyPriceQuantity);
                        dealBuyPriceQuantity.setQuantity(dealPriceQuantity.getQuantity() +
                                dealBuyPriceQuantity.getQuantity());
                    }else {
                        DealPriceQuantity dealBuyPriceQuantity = userBuyInfoMap.get(dealPriceQuantity.getUserId());
                        dealBuyPriceQuantity.setStatus(StockStatusEnum.BuyPartSuccess);
                        Double currPrice = (dealPriceQuantity.getQuantity() * dealPriceQuantity.getPrice()
                                + dealBuyPriceQuantity.getQuantity() * dealBuyPriceQuantity.getPrice())
                                / (dealPriceQuantity.getQuantity() + dealBuyPriceQuantity.getQuantity());
                        dealBuyPriceQuantity.setPrice(currPrice);
                        userBuyInfoMap.put(dealPriceQuantity.getUserId(), dealBuyPriceQuantity);
                        dealBuyPriceQuantity.setQuantity(dealPriceQuantity.getQuantity() +
                                dealBuyPriceQuantity.getQuantity());
                    }
                }else{
                    userBuyInfoMap.put(dealPriceQuantity.getUserId(), dealPriceQuantity);
                }
            }else if(dealPriceQuantity.getStatus() == StockStatusEnum.SalePartSuccess
                    || dealPriceQuantity.getStatus() == StockStatusEnum.SaleSuccess){
                if(userSaleInfoMap.containsKey(dealPriceQuantity.getUserId())){
                    if((userSaleInfoMap.get(dealPriceQuantity.getUserId()).getStatus() ==StockStatusEnum.SalePartSuccess
                            && dealPriceQuantity.getStatus() == StockStatusEnum.SaleSuccess)
                            || (userSaleInfoMap.get(dealPriceQuantity.getUserId()).getStatus() ==StockStatusEnum.SaleSuccess
                            && dealPriceQuantity.getStatus() == StockStatusEnum.SalePartSuccess)){
                        DealPriceQuantity dealSalePriceQuantity = userSaleInfoMap.get(dealPriceQuantity.getUserId());
                        dealSalePriceQuantity.setStatus(StockStatusEnum.SaleSuccess);
                        Double currPrice = (dealPriceQuantity.getQuantity() * dealPriceQuantity.getPrice()
                                + dealSalePriceQuantity.getQuantity() * dealSalePriceQuantity.getPrice())
                                / (dealPriceQuantity.getQuantity() + dealSalePriceQuantity.getQuantity());
                        dealSalePriceQuantity.setPrice(currPrice);
                        dealSalePriceQuantity.setQuantity(dealPriceQuantity.getQuantity() +
                                dealSalePriceQuantity.getQuantity());
                        userSaleInfoMap.put(dealPriceQuantity.getUserId(), dealSalePriceQuantity);
                    }else {
                        DealPriceQuantity dealSalePriceQuantity = userSaleInfoMap.get(dealPriceQuantity.getUserId());
                        dealSalePriceQuantity.setStatus(StockStatusEnum.BuyPartSuccess);
                        Double currPrice = (dealPriceQuantity.getQuantity() * dealPriceQuantity.getPrice()
                                + dealSalePriceQuantity.getQuantity() * dealSalePriceQuantity.getPrice())
                                / (dealPriceQuantity.getQuantity() + dealSalePriceQuantity.getQuantity());
                        dealSalePriceQuantity.setPrice(currPrice);
                        userSaleInfoMap.put(dealPriceQuantity.getUserId(), dealSalePriceQuantity);
                        dealSalePriceQuantity.setQuantity(dealPriceQuantity.getQuantity() +
                                dealSalePriceQuantity.getQuantity());
                    }
                }else{
                    userSaleInfoMap.put(dealPriceQuantity.getUserId(), dealPriceQuantity);
                }

            }
        }

        for(Long userId : userBuyInfoMap.keySet()){
            dealMapper.updateUserDealInfo(userBuyInfoMap.get(userId));
        }

        for(Long userId : userSaleInfoMap.keySet()){
            dealMapper.updateUserDealInfo(userBuyInfoMap.get(userId));
        }

        return true;
    }
}
