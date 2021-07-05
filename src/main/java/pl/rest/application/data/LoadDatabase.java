package pl.rest.application.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.rest.application.entity.Application;
import pl.rest.application.entity.Status;
import pl.rest.application.repository.ApplicationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));


    @Bean
    CommandLineRunner initDatabase(ApplicationRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Application(20210703144426467L, LocalDateTime.parse("2021-02-01 12:12:12", formatter),"Podanie o pracę", "W załączniku moje CV", Status.CREATED)));
            log.info("Preloading " + repository.save(new Application(20210703144424217L, LocalDateTime.parse("2021-02-01 12:12:12", formatter),"Wniosek urlopowy", "Chciałbym wziąć urlop w lipcu w okresie 15.07 - 20.07", Status.CREATED)));
            log.info("Preloading " + repository.save(new Application(20210703144426237L, LocalDateTime.parse("2021-02-01 12:12:12", formatter),"Wniosek o podwyżkę wynagrodzenia", "Proszę o podwyżkę wynagrodzenia o 25%", Status.CREATED)));
            log.info("Preloading " + repository.save(new Application(20210703144421127L, LocalDateTime.parse("2021-02-01 12:12:12", formatter),"Wniosek o wydanie dokumentu", "W załączniku wymagane dokumenty", Status.ACCEPTED)));
            log.info("Preloading " + repository.save(new Application(20210703144421127L, LocalDateTime.parse("2021-02-01 12:12:12", formatter),"Wniosek o wydanie dokumentu", "W załączniku wymagane dokumenty", Status.VERIFIED, true)));
            log.info("Preloading " + repository.save(new Application(20210703144421127L, LocalDateTime.parse("2021-02-01 12:12:12", formatter),"Wniosek o wydanie dokumentu", "W załączniku wymagane dokumenty", Status.CREATED, true)));
        };
    }
}
