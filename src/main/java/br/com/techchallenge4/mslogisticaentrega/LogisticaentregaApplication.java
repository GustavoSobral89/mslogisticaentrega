package br.com.techchallenge4.mslogisticaentrega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LogisticaentregaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticaentregaApplication.class, args);
	}

}
