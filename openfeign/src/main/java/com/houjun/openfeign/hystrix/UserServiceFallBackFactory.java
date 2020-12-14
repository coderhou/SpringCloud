package com.houjun.openfeign.hystrix;

import Entity.User;
import com.houjun.openfeign.service.UserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public User addUser1(User user) {
                System.out.println("降级工厂：addUser1"+user);
                return null;
            }

            @Override
            public User addUser2(User user) {
                System.out.println("降级工厂：addUser2"+user);
                return null;
            }

            @Override
            public void deleteUser1(Integer id) {
                System.out.println("降级工厂：deleteUser1"+id);
            }

            @Override
            public void deleteUser2(Integer id) {
                System.out.println("降级工厂：deleteUser2"+id);
            }

            @Override
            public void getUserByName(String name) throws UnsupportedEncodingException {
                System.out.println("降级工厂：getUserByName"+name);
            }
        };
    }
}
