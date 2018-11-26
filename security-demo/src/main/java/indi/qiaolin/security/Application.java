package indi.qiaolin.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author qiaolin
 * @version 2018/11/19
 **/


@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @RequestMapping("/hello")
    public String hello(){
        return "hello spring security";
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
