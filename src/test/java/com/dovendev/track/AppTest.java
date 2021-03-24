package com.dovendev.track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTest {
    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);
    }

    /*
Must-have test for each Spring Boot application for wiring up the whole ApplicationContext,
spanning all modules, to check if all dependencies between the beans are satisfied.
* */

    class ApplicationTests {
        @Test
        void applicationContextLoads() {
        }
    }
}
