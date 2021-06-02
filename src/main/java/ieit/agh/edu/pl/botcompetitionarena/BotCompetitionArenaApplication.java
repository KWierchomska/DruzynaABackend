package ieit.agh.edu.pl.botcompetitionarena;

import ieit.agh.edu.pl.botcompetitionarena.domain.queue.control.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

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

    @Scheduled(cron="*/5 * * * * *")
    public void printdate(){


    }

}
