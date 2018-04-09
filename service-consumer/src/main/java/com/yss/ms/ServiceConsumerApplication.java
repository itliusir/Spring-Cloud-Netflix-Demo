package com.yss.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 服务消费方
 *
 * @author liugang
 * @since 2018-04-08
 */
@SpringCloudApplication
@EnableFeignClients
@Configuration
public class ServiceConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceConsumerApplication.class, args);
	}

	/**
	 * restTemplate
	 *
	 * {@link LoadBalanced}	开启客户端负载均衡 默认轮询list.get(n次调用 % list.size)
	 * {@link org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient}
	 * {@link org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration}
	 * @author liugang 2018-04-08 14:48
	 * */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
