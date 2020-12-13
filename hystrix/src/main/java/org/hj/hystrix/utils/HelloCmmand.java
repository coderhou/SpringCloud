package org.hj.hystrix.utils;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

public class HelloCmmand extends HystrixCommand<String> {

    RestTemplate restTemplate;
    String name;
    public HelloCmmand(Setter setter, RestTemplate restTemplate) {
        super(setter);
        this.restTemplate = restTemplate;
    }

    public HelloCmmand(Setter setter, RestTemplate restTemplate, String name) {
        super(setter);
        this.restTemplate = restTemplate;
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
//        int i = 2 / 0;
//        String forObject = restTemplate.getForObject("http://provider/hello2", String.class);
//        return forObject;
        return restTemplate.getForObject("http://provider/hello2?name={1}",String.class,"houjun");

    }

    /**
     * 请求失败的回调
     * @return
     */
    @Override
    protected String getFallback() {
        return "error-extends"+this.getExecutionException().getMessage();
    }

    //指定 name作为缓存的 key
    @Override
    protected String getCacheKey() {
        return "houjun";
    }
}
