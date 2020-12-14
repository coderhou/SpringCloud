package org.hj.provider.controller;

import Entity.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 传统调用方式
 */
@RequestMapping("/user1")
@RestController
public class UserController {

    @PostMapping("/user1")
    public User addUser1(User user) {
        return user;
    }

    @PostMapping("/user2")
    public User addUser2(@RequestBody User user) {
        return user;
    }

    @PutMapping("/user1")
    public void updateUser1(User user) {
        System.out.println(user);
    }

    @PutMapping("/user2")
    public void updateUser2(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping("/user1")
    public void deleteUser1(Integer id) {
        System.out.println(id);
    }

   @DeleteMapping("/user2/{id}")
    public void deleteUser2(@PathVariable Integer id) {
        System.out.println(id);
    }

    @GetMapping("/user1")
    public void getByName(@RequestHeader String name) throws UnsupportedEncodingException {
        String decode = URLDecoder.decode(name,"UTF-8");
        System.out.println(decode);
    }
}
