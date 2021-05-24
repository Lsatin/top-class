package com.lsatin.topclass.web.context;

import com.lsatin.topclass.web.servlet.DashboardServlet;
import com.lsatin.topclass.web.servlet.SchoolServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        servletContext.addServlet("DashboardServlet", DashboardServlet.class)
                .addMapping("/", "/index", "/school", "/class", "/course", "/teacher", "/student");

        servletContext.addServlet("SchoolServlet", SchoolServlet.class)
                .addMapping("/school/*");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("销毁上下文监听器...");
        }
    }

}
