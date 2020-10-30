package com.lsatin.topclass.web.servlet;

import com.lsatin.topclass.web.base.servlet.BaseServlet;
import com.lsatin.topclass.web.pojo.School;
import com.lsatin.topclass.web.service.SchoolService;
import com.lsatin.topclass.web.service.impl.SchoolServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 学校servlet
 */
public class SchoolServlet extends BaseServlet {

    private static final SchoolService schoolService = new SchoolServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String service = req.getServletPath();
        String path = req.getPathInfo();
        logger.info("仪表盘GET请求[ SERVLET: {} ][ PATH: {} ]", service, path);
        try {
            if ("/list".equals(path)) {
                this.writerJson(resp, schoolService.getList(this.getRequestParameter(req, School.class)));
            } else if ("/save".equals(path)) {
                this.writerJson(resp, schoolService.saveSchool(this.getRequestParameter(req, School.class)));
            } else if ("/del".equals(path)) {
                this.writerJson(resp, schoolService.deleteSchool(this.getRequestParameter(req, School.class)));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
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
