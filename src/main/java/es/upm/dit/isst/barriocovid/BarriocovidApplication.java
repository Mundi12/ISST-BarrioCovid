package es.upm.dit.isst.barriocovid;
//./mvnw clean install spring-boot:run -DskipTests=true
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BarriocovidApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarriocovidApplication.class, args);
	}

}
