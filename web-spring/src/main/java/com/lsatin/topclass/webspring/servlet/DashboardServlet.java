package com.lsatin.topclass.webspring.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 仪表盘servlet
 */
public final class DashboardServlet extends HttpServlet {

    /** LOG4J */
    private final Logger LOGGER = LogManager.getLogger(getClass());

    /** 视图前缀 */
    private static final String VIEW_PREFIX = "/views/";

    /** 视图后缀 */
    private static final String VIEW_SUFFIX = ".jsp";

    @Override
    public void init() throws ServletException {
        LOGGER.info("{} Initialize...", getClass().getSimpleName());
        super.init();
    }

    @Override
    public void destroy() {
        LOGGER.info("{} destroy...", getClass().getSimpleName());
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String service = req.getServletPath();
        String path = req.getPathInfo();
        LOGGER.info("仪表盘GET请求[ SERVLET: {} ][ PATH: {} ]", service, path);
        if (service.isEmpty()) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "dashboard" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/index".equals(service) || "/".equals(service)) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "index" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/school".equals(service)) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "school" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/class".equals(service)) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "class" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/course".equals(service)) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "course" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/teacher".equals(service)) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "teacher" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/student".equals(service)) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "student" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/login".equals(service)) {
            req.getRequestDispatcher(req.getContextPath() + VIEW_PREFIX + "login" + VIEW_SUFFIX).forward(req, resp);
        } else if ("/signIn".equals(service)) {
            resp.sendRedirect("/");
        } else {
            resp.setStatus(404);
        }
    }

}
