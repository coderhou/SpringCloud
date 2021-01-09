package org.houjun.sentinel1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootTest
class Sentinel1ApplicationTests {

    @Test
    void contextLoads() {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 15; i++) {
            String forObject = restTemplate.getForObject("http://localhost:8081/hello", String.class);
            System.out.println(forObject+new Date());
        }
    }

}
