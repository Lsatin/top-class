package com.lsatin.topclass.web.servlet;

import com.lsatin.topclass.web.base.servlet.BaseServlet;
import org.apache.logging.log4j.web.WebLoggerContextUtils;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 仪表盘servlet
 */
public class DashboardServlet extends BaseServlet {

    @Override
    public void init() throws ServletException {
        logger.info("仪表盘servlet初始化...");
        super.init();
    }

    @Override
    public void destroy() {
        logger.info("仪表盘servlet销毁...");
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String service = req.getServletPath();
        String path = req.getPathInfo();
        logger.info("仪表盘GET请求[ SERVLET: {} ][ PATH: {} ]", service, path);
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

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doHead(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
       HttpSession session = req.getSession();
       session.setAttribute("username", req.getParameter("username"));
       this.doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }
}
