package com.javatechbd.redisdistributedlock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.IntStream;

@Configuration
public class ApplicationConfig {

  @Bean
  CommandLineRunner commandLineRunner(RedisService redisService,
                                      SalesRepository salesRepository) {
    return args -> {
      redisService.resetBucket();
      IntStream.rangeClosed(1, 10).parallel()
        .forEach(itm-> {
          System.out.println(itm);
          salesRepository.save(new SalesEntity(redisService.lock(),"instance-1"));
        });
    };
  }
}
