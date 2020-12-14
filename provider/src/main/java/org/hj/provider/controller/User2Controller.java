package org.hj.provider.controller;

import Entity.User;
import common.IUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
/**
 * OpenFeign 调用
 */
@RestController
public class User2Controller implements IUserService {

    @Override
    public User addUser1(User user) {
        return user;
    }

    @Override
    public User addUser2(@RequestBody User user) {
        return user;
    }

    @Override
    public void deleteUser1(Integer id) {
        System.out.println(id);
    }

    @Override
    public void deleteUser2(@PathVariable Integer id) {
        System.out.println(id);
    }

    @Override
    public void getUserByName(@RequestHeader String name) throws UnsupportedEncodingException {
        String decode = URLDecoder.decode(name,"UTF-8");
        System.out.println(decode);
    }
}
