package fu.de190010.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DE190010PostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DE190010PostServiceApplication.class, args);
    }

}
