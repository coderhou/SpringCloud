package com.houjun.openfeign.service;

import Entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("provider")
public interface HelloService {
    @GetMapping("/hello")
    String hello();

    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);

    @PostMapping("/user1")
    User addUser1(@RequestParam("user") User user);

    @PostMapping("/user2")
    User addUser2(@RequestBody User user);

    @DeleteMapping("/user1")
    public void deleteUser1(@RequestParam("id") Integer id);

    @DeleteMapping("/user1/{id}")
    public void deleteUser2(@PathVariable("id") Integer id);

    @GetMapping("/user1")
    void getUserByName(@RequestHeader("name") String name);
}
