package coen275.stockmarket.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyApplicationRunner{
    @Autowired
    DealSchedule dealSchedule;

    @PostConstruct
    public void init(){
        dealSchedule.updateQueue();
        System.out.println("DealMatch start running");
    }


}