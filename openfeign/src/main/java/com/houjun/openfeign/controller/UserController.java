package com.houjun.openfeign.controller;

import Entity.User;
import com.houjun.openfeign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/hello2")
    String hello2() throws UnsupportedEncodingException {
        User user = new User();
        user.setUsername("houjun");
        user.setAddress("xxxx");
        User user1 = userService.addUser1(user);
        User user2 = userService.addUser2(user);
        System.out.println(user1);
        System.out.println(user2);
        userService.deleteUser1(1);
        userService.deleteUser1(2);
        userService.getUserByName(URLEncoder.encode("侯军","UTF-8"));
        return "";
    }
}
