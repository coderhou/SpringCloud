package com.houjun.openfeign.hystrix;

import Entity.User;
import com.houjun.openfeign.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@Component
@RequestMapping("/userfallback")//类中重写的方法都继承有父类映射，这里加入requestMapping以区分
public class UserServiceFallBack implements UserService {
    @Override
    public User addUser1(User user) {
        System.out.println("降级：addUser1"+null);
        return null;
    }

    @Override
    public User addUser2(User user) {
        System.out.println("降级：addUser2"+null);
        return null;
    }

    @Override
    public void deleteUser1(Integer id) {
        System.out.println("降级：deleteUser1"+id);
    }

    @Override
    public void deleteUser2(Integer id) {
        System.out.println("降级：deleteUser2"+id);
    }

    @Override
    public void getUserByName(String name) throws UnsupportedEncodingException {
        System.out.println("降级：getUserByName"+name);
    }
}
