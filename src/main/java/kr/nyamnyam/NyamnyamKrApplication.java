package kr.nyamnyam;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "kr.nyamnyam.model.repository")
@EnableMongoRepositories(basePackages = "kr.nyamnyam.model.repository")
public class NyamnyamKrApplication {

	public static void main(String[] args) {

		SpringApplication.run(NyamnyamKrApplication.class, args);

	}

}
