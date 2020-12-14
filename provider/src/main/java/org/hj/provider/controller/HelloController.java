package org.hj.provider.controller;

import Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 传统调用方式-基本使用
 */
@RestController
public class HelloController {
    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    public String hello() {
        return "hello world" + port;
    }

    @GetMapping("/hello2")
    public String hello2(String name){
        System.out.println(new Date() +"---->"+name);
        return "hello"+name;
    }
    @GetMapping("/hello3/{ids}")
    public List<User> hello3(@PathVariable String ids){
        String[] split = ids.split(",");
        List<User> list = new ArrayList<>();
        for (String s : split) {
            User user = new User();
            user.setUsername(s);
            list.add(user);
        }
        System.out.println(new Date() +"---->"+ ids);
        return  list ;
    }



}
