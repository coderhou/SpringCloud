package com.houjun.openfeign.service;

import common.IUserService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("provider")
public interface UserService extends IUserService {
}
