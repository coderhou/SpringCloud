package org.hj.hystrix.service;

import Entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserService {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 支持合并多个多次请求，一同发送
     * @param ids
     * @return
     */
    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids){
        User[] users = restTemplate.getForObject("http://provider/hello3/{1}",User[].class, StringUtils.join(ids, ","));
        return Arrays.asList(users);
    }

    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "200")})
    public Future<User> getUserById(Integer id){
        return null;
    }

}
