package com.lsatin.topclass.webspringmybatis.context;

import com.lsatin.topclass.webspringmybatis.constants.Constant;
import com.lsatin.topclass.webspringmybatis.servlet.DispatcherServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 上下文加载器监听器
 * <p>参考{@code org.springframework.web.context.ContextLoaderListener}
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {

    private static final Logger LOGGER = LogManager.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("创建上下文监听器...");
        }
        final ServletContext servletContext = sce.getServletContext();

        servletContext.addServlet("dispatcherServlet", DispatcherServlet.class).addMapping("/");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(sce.getServletContext().getInitParameter("contextConfigLocation"));
        servletContext.setAttribute(Constant.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("销毁上下文监听器...");
        }
    }

}
