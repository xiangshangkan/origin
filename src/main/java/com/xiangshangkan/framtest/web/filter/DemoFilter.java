package com.xiangshangkan.framtest.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @auther: Administrator
 * @date: 2018/12/10 11:18
 */
public class DemoFilter implements Filter{
    /**
    * 由web容器来调用完成过滤器的初始化工作，通过该方法可获取在web.xml中指定的初始化参数
    * @author      zhouhui
    * @param
    * @date        2018/12/10 11:27
    */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Enumeration parameterNames = filterConfig.getInitParameterNames();
        String initParamValue1 = filterConfig.getInitParameter("what");
        String initParamValue2 = filterConfig.getInitParameter("bywho");
        ServletContext servletContext = filterConfig.getServletContext();
        System.out.println("DemoFilter@init ...");
        System.out.println("filterConfig.getInitParameterNames() = " + parameterNames);
        System.out.println("filterConfig.getInitParameter(\"what\") = " + initParamValue1);
        System.out.println("filterConfig.getInitParameter(\"bywho\") = " + initParamValue2);
    }
    /**
    * 这是一个完成过滤行为的方法，这个同样是上游过滤器调用的方法，引入的FilterChain对象提供了后续过滤器所要调用的信息，
     * 如果该过滤器是过滤器链中的最后一个过滤器，则将请求交给被请求资源。也可以直接给客户端返回响应信息
    * @author      zhouhui
    * @param
    * @date        2018/12/10 11:29
    */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("DemoFilter@doFilter ...");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    /**
    * 由web容器来调用释放资源，doFilter()中的所有活动都被该实例终止后，调用该方法
    * @author      zhouhui
    * @param
    * @date        2018/12/10 11:33
    */
    @Override
    public void destroy() {
        System.out.println("DemoFilter@destroy ...");
    }
}
