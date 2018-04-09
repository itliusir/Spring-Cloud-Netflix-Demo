package com.yss.ms.rest;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yss.ms.entity.User;
import com.yss.ms.exception.BaseException;
import com.yss.ms.feign.IServiceProvider;
import com.yss.ms.feign.IServiceSideCar;
import com.yss.ms.msg.ObjectRestResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * TODO
 *
 * @author liugang
 * @since 2018-04-08
 */
@Slf4j
@RestController
@RequestMapping("user")
public class ConsumerController {

    @Autowired
    private IServiceProvider iServiceProvider;

    @Autowired
    private IServiceSideCar iServiceSideCar;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 模拟超时
     *
     * @author liugang 2018-04-08 23:11
     * */
    @HystrixCommand(fallbackMethod = "error1")
    @GetMapping("")
    public User user() throws BaseException{
        return iServiceProvider.user();
    }

    public User error1(Throwable throwable) throws BaseException{
        log.error("进入回退方法，异常：" + throwable);
        User user = new User();
        user.setId(0);
        user.setAge(0);
        user.setName("Admin");
        user.setPort(8081);
        //throw new BaseException("ConsumerController.user error fallback",500);
        return user;
    }

    @GetMapping("/ribbon")
    public User user1() throws BaseException{
        return restTemplate.getForObject("http://service-provider/user",User.class);
    }

    /**
     * 模拟异常
     *
     * {@link HystrixCircuitBreaker.HystrixCircuitBreakerImpl}
     * @author liugang 2018-04-08 21:40
     * */
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = {FeignException.class})
    @GetMapping("/error")
    public User userError() throws BaseException{
        return iServiceProvider.error();
    }

    public User error(Throwable throwable) throws BaseException{
        log.error("进入回退方法，异常：" + throwable);
        User user = new User();
        user.setId(0);
        user.setAge(0);
        user.setName("Admin");
        user.setPort(8080);
        //throw new BaseException("ConsumerController.userError error fallback",500);
        return user;
    }

    /**
     * C#服务接口
     *
     * @author liugang 2018-01-17 17:11
     * */
    @GetMapping("/sidecar")
    public Object getProducts(){
        return iServiceSideCar.Get();
    }

}
