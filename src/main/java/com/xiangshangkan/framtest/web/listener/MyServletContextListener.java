package com.xiangshangkan.framtest.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @auther: Administrator
 * @date: 2018/12/10 14:08
 */
public class MyServletContextListener implements ServletContextListener {
   /**
   * 监听ServletContext对象的创建的方法
   * @author      zhouhui
   * @param
   * @date        2018/12/10 14:11
   */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContext对象被 创建了。。。");
    }
    /**
    * 监听ServletContext对象的销毁的方法
    * @author      zhouhui
    * @param
    * @date        2018/12/10 14:12
    */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContext对象被 销毁了。。。");
    }
}
