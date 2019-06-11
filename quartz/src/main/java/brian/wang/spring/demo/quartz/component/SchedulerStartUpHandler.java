package brian.wang.spring.demo.quartz.component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class SchedulerStartUpHandler implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("I am the start up handler!!!!!!");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://www.google.com").build();
        Response response = client.newCall(request).execute();
        System.out.println(response.getClass().getName());
    }

}