package org.Dudnik.Login.API;

import org.Dudnik.config.GlobalProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "main")
//@EnableConfigurationProperties({ GlobalProperties.class })
@EnableJpaRepositories ("org.Dudnik.database.repositories")
@EntityScan("org.Dudnik.database.entities")

public class LoginApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApiApplication.class, args);
	}

}
