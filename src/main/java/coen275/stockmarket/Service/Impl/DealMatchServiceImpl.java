package coen275.stockmarket.Service.Impl;

import coen275.stockmarket.Enum.StockStatusEnum;
import coen275.stockmarket.Mapper.*;
import coen275.stockmarket.Service.BuyService;
import coen275.stockmarket.Service.DealMatchService;
import coen275.stockmarket.data.DealPriceQuantity;
import coen275.stockmarket.data.UserBuyInfo;
import coen275.stockmarket.data.UserStocksInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;


@Component
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


    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserSaleInfoMapper userSaleInfoMapper;

    @Autowired
    UserBuyInfoMapper userBuyInfoMapper;


    Double minutePrice;


    Long stockId;



    @Override
    public Boolean updateDealQueue(Long stockId) {
        PriorityQueue<DealPriceQuantity> salePriorityQueue = new PriorityQueue<DealPriceQuantity>(new Comparator<DealPriceQuantity>() {
            @Override
            public int compare(DealPriceQuantity o1, DealPriceQuantity o2) {
                if(o1.getPrice() != o2.getPrice()){
                    return (int) (o1.getPrice() - o2.getPrice());
                }else if(o1.getQuantity() != o2.getQuantity()){
                    return (int) (o2.getQuantity() - o1.getQuantity());
                }else{
                    return 1;
                }
            }
        });

        PriorityQueue<DealPriceQuantity> buyPriorityQueue = new PriorityQueue<>(new Comparator<DealPriceQuantity>() {
            @Override
            public int compare(DealPriceQuantity o1, DealPriceQuantity o2) {
                if(o1.getPrice() != o2.getPrice()){
                    return (int) (o2.getPrice() - o1.getPrice());
                }else if(o1.getQuantity() != o2.getQuantity()){
                    return (int) (o2.getQuantity() - o1.getQuantity());
                }else{

                    return 1;
                }
            }
        });


        List<DealPriceQuantity> saleList = dealMapper.getUserStockInfoListByStatus(StockStatusEnum.Sale, StockStatusEnum.SalePartSuccess, stockId);

        List<DealPriceQuantity> buyList = dealMapper.getUserStockInfoListByStatus(StockStatusEnum.Buy, StockStatusEnum.BuyPartSuccess, stockId);

        Map<Long, DealPriceQuantity> userBuyInfoMap = new HashMap<>();

        Map<Long, DealPriceQuantity> userSaleInfoMap = new HashMap<>();

        List<DealPriceQuantity> dealPriceQuantitylist = new ArrayList<>();

        Double maxPrice = 0.0;

        Double minPrice = 0.0;

        boolean flag = false;


        for(DealPriceQuantity dealPriceQuantity : saleList){
            salePriorityQueue.add(dealPriceQuantity);
        }
        for(DealPriceQuantity dealPriceQuantity: buyList){
            buyPriorityQueue.add(dealPriceQuantity);
        }
        while (buyPriorityQueue.size() != 0 && salePriorityQueue.size() != 0){
            flag = true;
            DealPriceQuantity maxBuy = buyPriorityQueue.poll();
            DealPriceQuantity minSale = salePriorityQueue.poll();
            stockId = maxBuy.getStockId();

            double eachPrice = 0.0;
            int eachQuantity = 0;
            while(maxBuy.getQuantity() != 0 || minSale.getQuantity() != 0){
                if(maxBuy.getQuantity() == 0){
                    if(buyPriorityQueue.size() != 0){
                        maxBuy = buyPriorityQueue.poll();
                        if(maxBuy.getQuantity() > minSale.getQuantity()){

                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = minSale.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuyPartSuccess));
                            maxBuy.setQuantity(maxBuy.getQuantity() - minSale.getQuantity()) ;
                            minSale.setQuantity(0);
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

                        }else if(maxBuy.getQuantity() < minSale.getQuantity()){
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;

                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SalePartSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));


                            minSale.setQuantity(minSale.getQuantity() - maxBuy.getQuantity());
                            maxBuy.setQuantity(0);
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
                        }else{
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
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
                                    null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, maxBuy.getQuantity() - minSale.getQuantity(), StockStatusEnum.BuyPartSuccess));
                            maxBuy.setQuantity(maxBuy.getQuantity() - minSale.getQuantity()) ;
                            minSale.setQuantity(0);
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

                        }else if(maxBuy.getQuantity() < minSale.getQuantity()){
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, minSale.getQuantity() - maxBuy.getQuantity(), StockStatusEnum.SalePartSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                            minSale.setQuantity(minSale.getQuantity() - maxBuy.getQuantity());
                            maxBuy.setQuantity(0);
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

                        }else{
                            eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                            eachQuantity = maxBuy.getQuantity() ;
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                            dealPriceQuantitylist.add(new DealPriceQuantity(
                                    null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
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
                                null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuyPartSuccess));
                        maxBuy.setQuantity(maxBuy.getQuantity() - minSale.getQuantity()) ;
                        minSale.setQuantity(0);
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

                    }else if(maxBuy.getQuantity() < minSale.getQuantity()){
                        eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                        eachQuantity = maxBuy.getQuantity() ;
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SalePartSuccess));
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
                        int newQuantity = minSale.getQuantity() - maxBuy.getQuantity();
                        maxBuy.setQuantity(0);
                        minSale.setQuantity(newQuantity);
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
                    }else{
                        eachPrice = (maxBuy.getPrice() + minSale.getPrice()) / 2;
                        eachQuantity = maxBuy.getQuantity();
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null,minSale.getDealId(), minSale.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.SaleSuccess));
                        dealPriceQuantitylist.add(new DealPriceQuantity(
                                null, maxBuy.getDealId(), maxBuy.getUserId(), stockId, eachPrice, eachQuantity, StockStatusEnum.BuySuccess));
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
                        break;
                    }
                }

            }

        }
        if(!flag){
            return true;
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
                            && dealPriceQuantity.getStatus() == StockStatusEnum.BuySuccess) ||
                            (userBuyInfoMap.get(dealPriceQuantity.getUserId()).getStatus() ==StockStatusEnum.BuySuccess
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
            if(userBuyInfoMap.get(userId).getStatus() == StockStatusEnum.BuyPartSuccess){
                DealPriceQuantity dealPriceQuantity = new DealPriceQuantity();
                dealPriceQuantity.setStatus(StockStatusEnum.BuySuccess);
                dealPriceQuantity.setDealId(-1L);
                dealPriceQuantity.setPrice(userBuyInfoMap.get(userId).getPrice());
                dealPriceQuantity.setQuantity(userBuyInfoMap.get(userId).getQuantity());
                dealPriceQuantity.setId(userBuyInfoMap.get(userId).getId());
                dealPriceQuantity.setUserId(userBuyInfoMap.get(userId).getUserId());
                dealPriceQuantity.setStockId(userBuyInfoMap.get(userId).getStockId());
                dealMapper.insertSelective(dealPriceQuantity);
                long dealId = userBuyInfoMap.get(userId).getDealId();
                UserBuyInfo userBuyInfo =  userBuyInfoMapper.selectByDealId(dealId);
                int quantity = userBuyInfo.getQuantity();
                Double buyPrice = userBuyInfo.getBuyPrice();
                userBuyInfoMap.get(userId).setQuantity(quantity - userBuyInfoMap.get(userId).getQuantity());
                userBuyInfoMap.get(userId).setPrice(buyPrice);
            }
            dealMapper.updateUserDealInfo(userBuyInfoMap.get(userId));
        }

        for(Long userId : userSaleInfoMap.keySet()){
            if(userSaleInfoMap.get(userId).getStatus() == StockStatusEnum.SaleSuccess){
                userInfoMapper.updateUserInfoCash(userSaleInfoMap.get(userId).getPrice()
                        * userSaleInfoMap.get(userId).getQuantity(), userId);
            }else if(userSaleInfoMap.get(userId).getStatus() == StockStatusEnum.SalePartSuccess){
                DealPriceQuantity dealPriceQuantity = new DealPriceQuantity();
                dealPriceQuantity.setPrice(userSaleInfoMap.get(userId).getPrice());
                dealPriceQuantity.setQuantity(userSaleInfoMap.get(userId).getQuantity());
                dealPriceQuantity.setId(userSaleInfoMap.get(userId).getId());
                dealPriceQuantity.setUserId(userSaleInfoMap.get(userId).getUserId());
                dealPriceQuantity.setStockId(userSaleInfoMap.get(userId).getStockId());
                dealPriceQuantity.setStatus(StockStatusEnum.SaleSuccess);
                dealPriceQuantity.setDealId(-1L);
                dealMapper.insertSelective(dealPriceQuantity);
                int quantity = userSaleInfoMapper.selectByKey(userSaleInfoMap.get(userId).getDealId());
                Double cash = dealPriceQuantity.getPrice() * userSaleInfoMap.get(userId).getQuantity();
                userInfoMapper.updateUserInfoCash(cash, userId);
                int newQuantity = quantity - userSaleInfoMap.get(userId).getQuantity();
                userSaleInfoMap.get(userId).setQuantity(newQuantity);
            }
            dealMapper.updateUserDealInfo(userSaleInfoMap.get(userId));
        }

        String tName=Thread.currentThread().getName();
        System.out.println(stockId+"调用的线程名字："+tName);

        return true;
    }
}
