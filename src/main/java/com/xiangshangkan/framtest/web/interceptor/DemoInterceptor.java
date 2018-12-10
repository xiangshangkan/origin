package com.xiangshangkan.framtest.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**拦截器是链式调用
 * 拦截器与过滤器的区别：
 * 1、拦截器是基于java反射，而过滤器是基于函数回调
 * 2、拦截器不依赖于servlert容器，过滤器依赖于servlert容器
 * 3、拦截器只能对action起作用，而过滤器则可以对几乎所有的请求起作用
 * 4、拦截器可以访问action上下文、值栈里的对象，而过滤器只能在容器初始化时被调用一次
 * 5、拦截器可以获取IOC容器中的各个bean，而过滤器就不行，这点很重要，在拦截器里注入一个service，可以调用业务逻辑
 *
 * @author: Administrator
 * @date: 2018/12/8 16:19
 */
public class DemoInterceptor extends HandlerInterceptorAdapter {
    /**
    *  在执行Handler之前进行，即Controller方法调用之前执行，主要进行初始化操作或登录拦截，返回值为是否中断连接
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        System.out.println("preHandle  ...");
        return true;
    }

    /**
     * 在执行Handler之后，即Controller方法调用之后，视图渲染之前执行，主要对ModelAndView对象进行操作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mov){
        System.out.println("postHandle");
    }

    /**
     * 在整个请求结束之后，即渲染对应的视图之后进行，主要进行资源清理工作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex){
       System.out.println("afterCompletion");
    }
}
