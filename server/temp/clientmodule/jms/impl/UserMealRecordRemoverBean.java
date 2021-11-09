package com.comert.mhl.database.jms.impl;

import com.comert.mhl.database.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import java.time.LocalDateTime;

@Startup
@Singleton(name = "DataBaseTimerEJB")
@LocalBean
public class UserMealRecordRemoverBean {

    @EJB
    private MemberService memberService;

    @Resource
    private TimerService timerService;
    private Timer userMealRecordRemover;

    @PostConstruct
    public void begin(){
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.dayOfMonth(1);
        userMealRecordRemover = timerService.createCalendarTimer(scheduleExpression);
    }

    @PreDestroy
    public void cancel(){
        userMealRecordRemover.cancel();
        System.out.println("TimerBean have cancelled");
    }

    @Timeout
    private void remove(Timer timer){
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        int result = memberService.removeUsersMealRecords(lastMonth);
        System.out.println("toplam "+ result + " aylık kayıt silindi");
    }

}
