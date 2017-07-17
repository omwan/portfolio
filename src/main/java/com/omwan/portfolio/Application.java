package com.omwan.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for portfolio project.
 *
 * Created by olivi on 07/16/2017.
 */
@SpringBootApplication
@ComponentScan("com.omwan.portfolio")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
