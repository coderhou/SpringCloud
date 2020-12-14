package common;

import Entity.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


public interface IUserService {


    @PostMapping("/user1")
    User addUser1( User user);

    @PostMapping("/user2")
    User addUser2(@RequestBody User user);

    @DeleteMapping("/user1")
    void deleteUser1(@RequestParam("id") Integer id);

    @DeleteMapping("/user1/{id}")
    void deleteUser2(@PathVariable("id") Integer id);

    @GetMapping("/user1")
    void getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException;
}
