package phoenix.AM_PM.mainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MainserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainserviceApplication.class, args);
	}

}
