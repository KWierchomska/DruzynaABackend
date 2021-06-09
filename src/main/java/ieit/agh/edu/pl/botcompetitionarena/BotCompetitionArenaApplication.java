package ieit.agh.edu.pl.botcompetitionarena;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class BotCompetitionArenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotCompetitionArenaApplication.class, args);
    }

    @Bean
    public CommandLineRunner printApplicationBeansAtStartup(ApplicationContext context) {
        return args -> {
            System.out.println("Beans provided by Spring Boot:");

            String[] beansNames = context.getBeanDefinitionNames();
            Arrays.sort(beansNames);
            for (String beanName : beansNames) {
                System.out.println(beanName);
            }
        };
    }

}
