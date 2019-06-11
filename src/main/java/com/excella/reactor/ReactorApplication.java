package com.excella.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.excella.reactor.domain")
@EnableJpaRepositories("com.excella.reactor.repositories")
public class ReactorApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReactorApplication.class, args);
  }
}
