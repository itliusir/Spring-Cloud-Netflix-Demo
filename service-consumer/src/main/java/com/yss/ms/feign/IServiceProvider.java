package com.yss.ms.feign;

import com.yss.ms.entity.User;
import com.yss.ms.exception.BaseException;
import com.yss.ms.msg.ObjectRestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * feignClient interface
 *
 * @author liugang
 * @since 2018-04-08
 */
@FeignClient(name = "service-provider")
public interface IServiceProvider {

    /**
     * UserInfo
     *
     * @return User
     * @author liugang 2018-04-08 16:33
     * */
    @GetMapping("/user")
    User user() throws BaseException;

    /**
     * 模拟错误
     *
     * @return User
     * @author liugang 2018-04-08 16:40
     * */
    @GetMapping("/user/error")
    User error() throws BaseException;
}
