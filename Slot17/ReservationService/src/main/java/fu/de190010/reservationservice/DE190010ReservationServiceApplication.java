package fu.de190010.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DE190010ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DE190010ReservationServiceApplication.class, args);
    }

}
