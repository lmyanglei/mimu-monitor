package cn.mimukeji.backend.task;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("通过实现ApplicationRunner接口，在spring boot项目启动后执行，开始");

        try{

        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("通过实现ApplicationRunner接口，在spring boot项目启动后执行，完成");
    }


}