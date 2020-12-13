package org.hj.consumer.controller;

import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;

@RestController
public class HelloController {
    //写死的服务调用
    @GetMapping("/hello")
    public String hello() {
        URL url = null;
        try {
            url = new URL("http://localhost:1113/hello");
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "访问失败";
    }

    /**
     * 从注册中心拿到的服务信息
     *
     * @return
     */
    @Autowired
    DiscoveryClient discoveryClient;
    //线性负载均衡
    Integer count = 0;

    @GetMapping("/hello2")
    public String hello2() {
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        ServiceInstance instance = list.get((count++) % list.size());
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer("http://");
        sb.append(host).append(":").append(port).append("/hello");
        URL url = null;
        try {
            url = new URL(sb.toString());
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "访问失败";
    }

    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplateOne;

    @GetMapping("/hello3")
    public String hello3() {
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        ServiceInstance instance = list.get((count++) % list.size());
        String host = instance.getHost();
        int port = instance.getPort();
        StringBuffer sb = new StringBuffer("http://");
        sb.append(host).append(":").append(port).append("/hello");
        String s = restTemplateOne.getForObject(sb.toString(), String.class);
        return s;
    }

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @GetMapping("/hello4")
    public String hello4() {
        String s = restTemplate.getForObject("http://provider/hello", String.class);
        return s;
    }

    @GetMapping("/hello5")
    public String hello5() {
        String s = restTemplate.getForObject("http://provider/hello2?name={1}", String.class, "houjun");
        return s;
    }

    @GetMapping("/hello6")
    public String hello6() {
        HashMap<Object, Object> map = new HashMap<>(2);
        String s = restTemplate.getForObject("http://provider/hello2?name={name}", String.class, map);
        return s;
    }

    @GetMapping("/hello7")
    public String hello7() throws UnsupportedEncodingException {
        String url = "http://provider/hello2?name=" + URLEncoder.encode("張三", "UTF-8");
        URI uri = URI.create(url);
        return restTemplate.getForObject(uri, String.class);
    }



}
