package com.houjun.openfeign.service;

import com.houjun.openfeign.hystrix.UserServiceFallBack;
import com.houjun.openfeign.hystrix.UserServiceFallBackFactory;
import common.IUserService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 方式2 使用OpenFeign，和provider 都继承公共类
 */
//@FeignClient(value = "provider",fallback = UserServiceFallBack.class)
@FeignClient(value = "provider",fallbackFactory = UserServiceFallBackFactory.class)
public interface UserService extends IUserService {
}
