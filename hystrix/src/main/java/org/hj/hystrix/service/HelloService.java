package org.hj.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 在这个方法中，我们将发起一次远程调用，去调用provider 中提供的/hello 接口
     * 但是这个调用可能会失败，
     * 我们在这个方法上加上@HyxtrixCommand注解 配置fallbackMethod属性，这个属性表示
     * 该方法调用失败时的临时替代方法。----这种操作叫做服务降级
     * ignoreExceptions:表示抛出此异常时，不进行服务降级
     * @return
     */
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public String hello(){
        int i = 2 / 0;
        return restTemplate.getForObject("http://provider/hello",String.class);
    }

    /**
     * 注意这个方法名字要和fallbackMethod 一致，
     * 方法返回值也要和对应的方法一致
     * @return
     */
    public String error(Throwable throwable){
        return "error"+throwable;
    }


    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello3() {
       return new AsyncResult<String>(){

            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello", String.class);
            }
        };
    }

    /**
     * 支持缓存的请求
     * @CacheResult：这个注解表示该方法的请求结果会被缓存起来，默认情况下，缓存的 key就是方法的
     * 参数，缓存的value 就是请求的结果
     * @return
     */
    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult
    public String hello4(@CacheKey String name,Integer age){
        return restTemplate.getForObject("http://provider/hello2?name={1}",String.class,"houjun");
    }

    public String error2(String name,Integer age){
        return "error";
    }

    @HystrixCommand
    @CacheRemove(commandKey = "hello4")
    public void del(){

    }
}
