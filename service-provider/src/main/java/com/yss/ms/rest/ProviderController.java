package com.yss.ms.rest;

import com.yss.ms.entity.User;
import com.yss.ms.exception.BaseException;
import com.yss.ms.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test
 *
 * @author liugang
 * @since 2018-04-08
 */
@RestController
@RequestMapping("user")
public class ProviderController {

    @Autowired
    private User user;

    @GetMapping("")
    public User user() throws BaseException {
        return user;
    }

    @GetMapping("/error")
    public User error() throws BaseException{
        throw new BaseException(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase(), HttpStatus.GATEWAY_TIMEOUT.value());
    }
}
