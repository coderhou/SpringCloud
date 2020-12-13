package org.hj.hystrix.utils;

import Entity.User;
import com.netflix.hystrix.*;
import org.hj.hystrix.service.UserService;

import java.util.List;

public class UserBatchCommand extends HystrixCommand<List<User>> {
    private List<Integer> ids;
    private UserService userService;

    public UserBatchCommand( List<Integer> ids, UserService userService) {
       super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd")).andCommandKey(HystrixCommandKey.Factory.asKey("batchkey")));
        this.ids = ids;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.getUserByIds(ids);
    }


}
