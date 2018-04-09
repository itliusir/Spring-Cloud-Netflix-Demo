# RPAS基础Common层(迭代中...)

- **主要根据业务层次的增删改查对JPA做了一层封装**
- **对异常进行统一处理**
- **对返回信息的统一封装**
- **工具类和异常码的定义**
- **通用条件查询的封装**

---------

# Doc

## 打包(稳定后再传私服)

```shell
    1. git clone http://1.119.186.147:8085/3.0/Robot/ms-rpas/ms-rpas-common
    2. 项目根目录下执行mvn clean install
```

## 依赖层

```xml
    <dependency>
        <groupId>com.yss.ms</groupId>
        <artifactId>ms-rpas-common</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

## 条件查询Doc

### Rest层

`POST:http://ip:port/demo/page?pageNum=1&pageSize=10`

`Body`体:

```json
    [
    	{
    		"paramKey":"activitiId",
    		"paramValue":"activiti1",
    		"condition":"EQUAL"
    	}
    ]
```

`Body`参数介绍：
- `paramKey`: 属性名
- `paramValue`: 属性值
- `condition`: 参数条件

参数条件目前支持类型:

```java
    public enum ParamCondition {
    
        /**
         * 等于
         * */
        EQUAL,
        /**
         * 大于
         * */
        GREATERTHAN,
        /**
         * 小于
         * */
        LESSTHAN,
        /**
         * 模糊查询
         * */
        LIKE,
        /**
         * 大于等于
         * */
        GREATERTHANEQUAL,
        /**
         * 小于等于
         * */
        LESSTHANEQUAL
    }
```

### Service/Biz层

- 带分页参数

```java
    Page<TaskInfo> pageBean = xxxBiz.findByCondition(pageNum,pageSize,selectParams);
```

- 不带分页参数(用于服务调用数据筛选)

```java
    List<T> list = xxxBiz.findByCondition(selectParams);
```
## Entity层

```java
    @EqualsAndHashCode(callSuper = true)
    @Entity
    @Table(name = "t_task_info")
    @Data
    @DynamicUpdate
    @DynamicInsert
    public class TaskInfo extends BaseEntity implements Serializable {}
```

## Dao层

```java
    public interface TaskInfoRepository extends BaseJpaRepository<TaskInfo,Integer> {}
```

## Service层

```java
    @Service
    public class TaskMgmentBiz extends BaseService<TaskInfoRepository,TaskInfo,Integer>{}
```