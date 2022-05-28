package coen275.stockmarket.scheduler;

import coen275.stockmarket.Service.DealMatchService;
import coen275.stockmarket.Service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DealSchedule
{
    @Autowired
    DealMatchService dealMatchService;

    @Autowired
    StockService stockService;

    @Scheduled(cron = "*/15 * * * * ?")
    public void updateQueue() {
        List<Long> stockId = stockService.selectStockNumber();
        for(int i = 0; i < stockId.size(); i++){
            Boolean matchSuccess = dealMatchService.updateDealQueue(stockId.get(i));
            if(matchSuccess){
                System.out.println("完成stock"+stockId.get(i) + "的队列交易的更新");
            }else{
                System.out.println("stock" + stockId.get(i) + "的队列交易更新失败");
            }
        }

    }
}
