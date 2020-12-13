package org.hj.hystrix.controller;

import Entity.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.hj.hystrix.service.HelloService;
import org.hj.hystrix.service.UserService;
import org.hj.hystrix.utils.HelloCmmand;
import org.hj.hystrix.utils.UserCollapseCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }

    // 继承方式实现调用
    @GetMapping("/hello2")
    public void hello2(){
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();//初始化缓存
        HelloCmmand helloCmmand = new HelloCmmand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("houjun")), restTemplate);
        String execute = helloCmmand.execute();
        System.out.println("方式一： "+execute);

        HelloCmmand helloCmmand2 = new HelloCmmand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("houjun")), restTemplate);
        Future<String> queue = helloCmmand2.queue();
        try {
            String s = queue.get();
            System.out.println("方式二： "+s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ctx.close();// 关闭缓存上下文
    }

    @GetMapping("/hello3")
    public String hello3(){
        Future<String> stringFuture = helloService.hello3();
        String s = null;
        try {
            s = stringFuture.get();
            return s;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return s;
    }

    @GetMapping("/hello4")
    public String hello4(){
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        String s = helloService.hello4("java",10);
        s = helloService.hello4("java",10);
        ctx.close();
        return s;
    }


    @GetMapping("/hello5")
    public String hello5() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        UserCollapseCommand cmd1 = new UserCollapseCommand(userService, 91);
        UserCollapseCommand cmd2 = new UserCollapseCommand(userService, 92);
        UserCollapseCommand cmd3 = new UserCollapseCommand(userService, 93);
        UserCollapseCommand cmd4 = new UserCollapseCommand(userService, 94);
        Future<User> q1 = cmd1.queue();
        Future<User> q2 = cmd2.queue();
        Future<User> q3 = cmd3.queue();
        Future<User> q4 = cmd4.queue();
        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        User u4 = q4.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        ctx.close();
        return "s";
    }

    @GetMapping("/hello6")
    public String hello6() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        Future<User> f1 = userService.getUserById(99);
        Future<User> f2 = userService.getUserById(98);
        Future<User> f3 = userService.getUserById(97);
        Future<User> f4 = userService.getUserById(96);

        User u1 = f1.get();
        User u2 = f2.get();
        User u3 = f3.get();
        User u4 = f4.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        ctx.close();
        return "s";
    }
}
