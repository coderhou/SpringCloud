package org.houjun.sleuth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
    private static final Log log = LogFactory.getLog(HelloController.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HellService hellService;
    @GetMapping("/hello")
    public String hello() {
        log.info("hello spring cloud sleuth");
        return "hello spring cloud sleuth";
    }

    @GetMapping("/hello2")
    public String hello2() throws InterruptedException {
        log.info("hello2");
        Thread.sleep(500);
        return restTemplate.getForObject("http://localhost:8080/hello3", String.class);
    }

    @GetMapping("/hello3")
    public String hello3() throws InterruptedException {
        log.info("hello3");
        Thread.sleep(500);
        return "Hello world3";
    }
    @GetMapping("/hello4")
    public String hello4() throws InterruptedException {
        log.info("hello4");
        Thread.sleep(500);
        return hellService.backgroundFun();
    }
    @GetMapping("/hello5")
    public String hello5() throws InterruptedException {
        log.info("hello5");
        Thread.sleep(500);
        hellService.Schedule();
        return "scheme";
    }

}
