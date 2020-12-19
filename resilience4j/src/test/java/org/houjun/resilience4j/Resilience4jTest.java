package org.houjun.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;

public class Resilience4jTest {

    @Test
    public void test1(){
        // 获取CircuitBreakerRegistry 实例，可以调用ofDefaults获取默认的实例，也可以自定义传入配置
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                //故障率阈值百分比,超过这个值，会开启断路器
                .failureRateThreshold(50)
                //断路器保持打开的时间，在到达设置的时间之后，断路器会进入到half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //当断路器处于half open 状态时，环形缓冲区的大小
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = r1.circuitBreaker("houjun");
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        Try<String> result = Try.of(supplier).map(v -> v + "hello world");
        System.out.println(result);
    }

    @Test
    public void test2(){
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                //故障率阈值百分比,超过这个值，会开启断路器
                .failureRateThreshold(50)
                //断路器保持打开的时间，在到达设置的时间之后，断路器会进入到half open 状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //当断路器处于half open 状态时，环形缓冲区的大小
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);
        CircuitBreaker cb1 = r1.circuitBreaker("houjun");
        System.out.println(cb1.getState());//获取断路器的一个状态。
        cb1.onError(0,new RuntimeException());
        System.out.println(cb1.getState());//获取断路器的一个状态。
        cb1.onError(0,new RuntimeException());
        System.out.println(cb1.getState());//获取断路器的一个状态。
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");
        Try<String> result = Try.of(supplier).map(v -> v + "hello world");
        System.out.println(result);
        System.out.println(result.isSuccess());
    }

    // 限流测试
    @Test
    public void limiter(){
        RateLimiterConfig config = RateLimiterConfig.custom()//注意，属性添加顺序不可随意。
                .limitRefreshPeriod(Duration.ofMillis(1000))
                // 每秒限制查询
                .limitForPeriod(2)
                .timeoutDuration(Duration.ofMillis(1000))
                .build();
        RateLimiterRegistry limiterRegistry = RateLimiterRegistry.of(config);
        RateLimiter limiter = limiterRegistry.rateLimiter("houjun");
//        RateLimiter limiter = RateLimiter.of("houjun", config);
        CheckedRunnable checkedRunnable = RateLimiter.decorateCheckedRunnable(limiter, () -> System.out.println(new Date()));
        Try.run(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .onFailure(t -> System.out.println(t.getMessage()));
    }

    // 重试测试
    @Test
    public void retry(){
        RetryConfig config = RetryConfig.custom()
                // 重试次数
                .maxAttempts(5)
                // 重试间隔
                .waitDuration(Duration.ofMillis(500))
                // 重试异常
                .retryExceptions(RuntimeException.class).build();
        Retry retry = Retry.of("houjun", config);
        Retry.decorateRunnable(retry, new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count++ <3 ){
                    System.out.println(count);
                    throw new RuntimeException();
                }
            }
        }).run();
    }
}
