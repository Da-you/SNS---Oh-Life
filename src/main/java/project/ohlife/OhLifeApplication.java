package project.ohlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import project.ohlife.common.properties.SmsAppProperties;


@SpringBootApplication
@EnableConfigurationProperties(value = SmsAppProperties.class)
public class OhLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OhLifeApplication.class, args);
	}

}
