package hr.abysalto.hiring.mid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "hr.abysalto.hiring.mid.client")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
