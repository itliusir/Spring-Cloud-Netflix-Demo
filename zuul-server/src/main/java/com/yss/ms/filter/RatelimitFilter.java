package com.yss.ms.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * TODO
 *
 * @author liugang
 * @since 2018-04-08
 */
public class RatelimitFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        return null;
    }
}
