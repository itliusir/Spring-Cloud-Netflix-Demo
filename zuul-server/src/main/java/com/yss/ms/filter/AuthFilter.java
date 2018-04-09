package com.yss.ms.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * 过滤器
 *
 * @author liugang
 * @since 2018-04-08
 */
public class AuthFilter extends ZuulFilter{
    /**
     * 过滤器类型
     * - pre 路由之前执行
     * - route 路由请求时被调用
     * - post 在route和error过滤器之后被过滤
     * - error 处理请求发生错误时候被调用
     *
     * @author liugang 2018-04-08 22:06
     * */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序,数值越小，优先级越高
     *
     * @author liugang 2018-04-08 22:09
     * */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否被执行,可结合yaml配置控制开启
     *
     * @author liugang 2018-04-08 22:07
     * */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 过滤器具体逻辑
     *
     * @author liugang 2018-04-08 22:11
     * */
    @Override
    public Object run() {
        return null;
    }
}
