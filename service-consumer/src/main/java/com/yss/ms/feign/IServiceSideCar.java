package com.yss.ms.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author liugang
 * @since 2018-04-08
 */
@FeignClient(name = "ms-sidecar")
public interface IServiceSideCar {

    @RequestMapping("/api/products")
    Object Get();
}
