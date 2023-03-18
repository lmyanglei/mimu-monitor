package cn.mimukeji.backend.business.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.mimukeji.backend.business.service.CronService;
import org.apache.log4j.Logger;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

@Component(value = "cronService")
public class CronServiceImpl implements CronService {

    Logger logger = Logger.getLogger(getClass());
    
    @Override
    public boolean isOnTime(String cron) {
        boolean returnValue = false;
        
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();

        try {
            cronTriggerImpl.setCronExpression(cron);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 2);// 把统计的区间段设置为从现在到1月后的今天（主要是为了方法通用考虑)

        // 这里的时间是根据corn表达式算出来的值
        List<Date> dates = TriggerUtils.computeFireTimesBetween(
                cronTriggerImpl, null, now,
                calendar.getTime());

        for (Date date : dates) {
            long diff = Math.abs(now.getTime() - date.getTime());
            if(diff <= 1000 * 60){
                returnValue = true;
                break;
            }
            
        }
        
        
        
        return returnValue;
    }
}
