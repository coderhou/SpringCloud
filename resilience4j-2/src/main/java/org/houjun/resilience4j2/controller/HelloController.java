package org.houjun.resilience4j2.controller;

import com.netflix.discovery.converters.Auto;
import org.houjun.resilience4j2.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("/hello5")
    public String hello5() {
        helloService.hello5();
        return "success";
    }
}
