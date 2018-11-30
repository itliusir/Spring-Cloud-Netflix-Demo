# `Spring Cloud Netflix OSS`

提供了对`Netflix`开源项目的集成，使我们可以以Spring Boot编程风格使用`Netflix`旗下相关框架，只需要在程序里添加注解，就可以使用成熟的`Netflix`组件(`Eureka`、`Hystrix`、`Zuul`、`Ribbon`、`Sidecar`)

## Spring Cloud Eureka

### Eureka客户端

- 向`Eureka`注册服务

- 高可用(HA)

  - 多注册中心主机

    如果配置了多个Eureka注册服务器，那么默认情况只有一台可用的服务器，存在注册信息。如果Down掉了，则会选择下一台可用的Eureka服务器。

- 配置

  - 应用间隔

    `registry-fetch-interval-seconds: `30 去服务端获取注册信息的间隔时间

  - 同步间隔

    `instance-info-replication-interval-seconds: `30 更新实例信息的变化到服务端的间隔时间

  [参考链接](http://cloud.spring.io/spring-cloud-static/Dalston.SR4/single/spring-cloud.html#_appendix_compendium_of_configuration_properties)

- 注意

  - 端口不要使用0
  - Eureka缓存
    1. Eureka Server对注册列表进行缓存，默认时间为30s。
    2. Eureka Client对获取到的注册信息进行缓存，默认时间为30s。
    3. Ribbon会从上面提到的Eureka Client获取服务列表，将负载均衡后的结果缓存30s。

### Eureka服务端

![img](http://qiniu.itliusir.com/Eureka.png)

- 注册中心对比

|   `Feature`    | [Consul](https://github.com/hashicorp/consul) | [zookeeper](https://github.com/apache/zookeeper) | [etcd](https://github.com/coreos/etcd) | [euerka](https://github.com/Netflix/eureka) |
| :------------: | :--------------------------------------: | :--------------------------------------: | :------------------------------------: | :--------------------------------------: |
|     服务健康检查     |               服务状态，内存，硬盘等                |            (弱)长连接，`keepalive`            |                  连接心跳                  |                   可配支持                   |
|     多数据中心      |                    支持                    |                    —                     |                   —                    |                    —                     |
|     kv存储服务     |                    支持                    |                    支持                    |                   支持                   |                    —                     |
|      一致性       |                  `raft`                  |                 `paxos`                  |                 `raft`                 |                    —                     |
|     `cap`      |                   `ca`                   |                   `cp`                   |                  `cp`                  |                   `ap`                   |
|  使用接口(多语言能力)   |              支持`http`和`dns`              |                   客户端                    |              `http/grpc`               |             `http`（sidecar）              |
|    watch支持     |            全量/支持long polling             |                    支持                    |            支持 long polling             |          支持 long polling/大部分增量           |
|      自身监控      |                `metrics`                 |                    —                     |               `metrics`                |                `metrics`                 |
|       安全       |               `acl /https`               |                  `acl`                   |              `https`支持（弱）              |                    —                     |
| spring cloud集成 |                   已支持                    |                   已支持                    |                  已支持                   |                   已支持                    |

- CAP

  - *C* **数据一致性**	 一致性是指数据的原子性，在经典的数据库中通过事务来保障，事务完成时，无论成功或回滚，数据都会处于一致的状态，在分布式环境下，一致性是指多个节点数据是否一致

    [raft](http://thesecretlivesofdata.com/raft/) 

  - *A* **服务可用性** 服务一直保持可用的状态，当用户发出一个请求，服务能在一定的时间内返回结果

  - *P* **网络分区故障的容错性** 在分布式应用中，可能因为一些分布式的原因导致系统无法运转，好的分区容忍性，使应用虽然是一个分布式系统，但是好像一个可以正常运转的整体

- Consul

  - 服务发现
  - 健康检查
  - 键值存储
  - 多数据中心

  [官网](https://www.consul.io/intro/index.html)

  [Spring Cloud Consul 参考文档](http://cloud.spring.io/spring-cloud-consul/)

- Eureka Server高可用配置

  ```yaml
  ---
  spring:
    profiles: peer1
  eureka:
    instance:
      hostname: peer1
    client:
      serviceUrl:
        defaultZone: http://peer2/eureka/

  ---
  spring:
    profiles: peer2
  eureka:
    instance:
      hostname: peer2
    client:
      serviceUrl:
        defaultZone: http://peer1/eureka/
  ```

  [参考文档](http://cloud.spring.io/spring-cloud-static/Dalston.SR4/single/spring-cloud.html#_peer_awareness)

## Spring Cloud Ribbon

主要功能是为REST客户端实现负载均衡

### Netflix Ribbon

- 依赖

  ```xml
  <dependency>
  	<groupId>org.springframework.cloud</groupId>
  	<artifactId>spring-cloud-starter-ribbon</artifactId>
  </dependency>
  ```

- Ribbon 客户端

  ```java
  @SpringBootApplication
  @RibbonClients({
          @RibbonClient(name = "service-provider")
  })
  public class Application {
  	@Bean
  	public RestTemplate restTemplate(){
  		return new RestTemplate();
  	}
  }
  ```

- 配置

  `application.properties`

  ```properties
  service-provider.ribbon.listOfServers = \
    http://${host}:${port}
  ```

### Netflix Ribbon 整合 Eureka

- Ribbon 客户端

  ```java
  @SpringBootApplication
  @RibbonClients({
          @RibbonClient(name = "service-provider")
  })
  @EnableDiscoveryClient
  public class Application {
  	@Bean
    	@LoadBalanced
  	public RestTemplate restTemplate(){
  		return new RestTemplate();
  	}
  }
  ```

- 配置

## Spring Cloud OpenFeign

- 发展

  9.0.0版本之后groupId `io.netflix.feign`更改为`io.github.openfeign`

  对应依赖`spring-cloud-starter-feign`-->`spring-cloud-starter-openfeign`

- 依赖

  ```xml
  <dependency>
  	<groupId>org.springframework.cloud</groupId>
  	<artifactId>spring-cloud-starter-feign</artifactId>
  </dependency>
  ```

- feign 客户端

  `Application.java`

  ```java
  @SpringBootApplication
  @EnableFeignClients
  public class Application {

      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }

  }
  ```

  `feignClient.java`

  ```java
  @FeignClient(value = "ms-business-task-engine-server")
  public interface ITaskEngineService {
      @RequestMapping(value = "/TaskQueue/addTaskToQueue",method = RequestMethod.POST)
      BaseResponse addTaskToQueue(@RequestBody List<SubTaskDTO> subTaskDTOList);
  }

  ```

- 配置参考ribbon

## Spring Cloud Hystrix

hystrix可帮助隔离每个服务，使单个服务的响应失败，避免微服务架构中因个别服务出现异常而引起级联故障蔓延。

### 特性

- 断路器机制(断路-->半开-->恢复)
- 资源隔离
- 熔断降级

### Hystrix Dashboard 监控

## Spring Cloud Zuul

在没有网关的时候，随着系统不断庞大，运维维护越来越复杂，接口校验逻辑的冗余越来越多，校验逻辑升级更为复杂。

### ZuulFilter

#### 过滤器类型

* pre 路由之前执行
* route 路由请求时被调用
* post 在route和error过滤器之后被过滤
* error 处理请求发生错误时候被调用

#### 过滤器执行顺序

- order越小，优先级越高

#### 过滤器是否被执行

- shouldFilter = true(结合yaml控制开启)

#### 过滤器具体逻辑

- run()

### Routes

路由规则与列表

## Spring Cloud Sidecar

### 非JVM语言接入SpringCloud的两种方案

- Sidecar
  - 必须去实现一个健康检查接口
  - 只有状态，服务治理只能从网关层控制流量
- 自己实现注册中心API Http接口(推荐)
