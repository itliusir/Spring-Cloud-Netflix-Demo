package com.yss.ms;

import com.yss.ms.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务提供方
 *
 * @author liugang
 * @since 2018-04-08
 */
@SpringCloudApplication
@Configuration
public class ServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderApplication.class, args);
	}

	@Value("${server.port}")
	int port;

	@Bean
	User user(){
		User user = new User();
		user.setId(1);
		user.setAge(18);
		user.setName("小明");
		user.setPort(port);
		return user;
	}

}
