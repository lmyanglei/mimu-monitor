package cn.mimukeji.backend.task;

import cn.mimukeji.backend.business.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class Task {

    @Autowired
    private MonitorService monitorService;

    @Scheduled(cron = "0 * * * * ? ")
    public void  checkoutMicroService() {
        try{

            monitorService.checkoutMicroService();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
