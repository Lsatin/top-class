package com.lsatin.topclass.webspringmybatis.servlet;

import com.lsatin.topclass.webspringmybatis.constants.Constant;
import org.springframework.context.ApplicationContext;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private ApplicationContext applicationContext;

    private static final Map<String, Class<? extends Servlet>> URL_MAPPING = new LinkedHashMap<>();
    static {
        URL_MAPPING.put("/index", DashboardServlet.class);
        URL_MAPPING.put("/school", DashboardServlet.class);
        URL_MAPPING.put("/class", DashboardServlet.class);
        URL_MAPPING.put("/course", DashboardServlet.class);
        URL_MAPPING.put("/teacher", DashboardServlet.class);
        URL_MAPPING.put("/student", DashboardServlet.class);

        URL_MAPPING.put("/school/*", SchoolServlet.class);
    }

    @Override
    public void init() throws ServletException {
        initApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String contextPath = req.getServletPath();
        Servlet bean = applicationContext.getBean(URL_MAPPING.get(handleUrl(contextPath)));
        bean.service(req, resp);
    }

    private void initApplicationContext() {
        Object attribute = this.getServletContext().getAttribute(Constant.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (attribute instanceof ApplicationContext) {
            this.applicationContext = (ApplicationContext) attribute;
            return;
        }
        throw new IllegalStateException("servlet上下文中，没有找到ApplicationContext");
    }

    private String handleUrl(String path) {
        if (path == null || path.length() < 1) {
            return path;
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        String[] split = path.split("/");
        if (split.length > 2) {
            return "/" + split[1] + "/*";
        }
        return path;
    }
}
