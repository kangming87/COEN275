package coen275.stockmarket.scheduler;

import coen275.stockmarket.Service.DealMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DealSchedule {
    @Autowired
    DealMatchService dealMatchService;

    //@Scheduled(cron = "*/2 * * * * ?")
    public void updateGoodsStatus() {

        Boolean matchSuccess = dealMatchService.updateDealQueue();
        log.info("完成队列交易的更新");
        if(matchSuccess){
            updateGoodsStatus();
            log.info("上一次队列交易完成，开始下一次");
        }
    }
}
