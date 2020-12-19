package org.houjun.resilience4j2.service;

import com.netflix.discovery.converters.Auto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@Retry(name = "retryA")
@CircuitBreaker(name = "cbA",fallbackMethod = "error")
public class HelloService {
    @Autowired
   private RestTemplate restTemplate;

    public String hello(){
        return restTemplate.getForObject("http://localhost:1113/hello4",String.class);
    }

    public String error(Throwable throwable){
        System.out.println("error"+throwable.getMessage());
        return "error"+throwable.getMessage();
    }

    public String hello5(){
        for (int i = 0; i < 5; i++) {
          restTemplate.getForObject("http://localhost:1113/hello5",String.class);
        }
        return "success";
    }

}

