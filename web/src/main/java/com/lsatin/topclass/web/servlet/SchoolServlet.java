package com.lsatin.topclass.web.servlet;

import com.lsatin.topclass.basic.pojo.Pagination;
import com.lsatin.topclass.basic.pojo.RestfulResult;
import com.lsatin.topclass.web.basic.servlet.BasicServlet;
import com.lsatin.topclass.web.pojo.School;
import com.lsatin.topclass.web.service.SchoolService;
import com.lsatin.topclass.web.service.impl.SchoolServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 学校servlet
 */
public class SchoolServlet extends BasicServlet<School, SchoolService> {

    private static SchoolService schoolService;

    @Override
    protected void setService(SchoolService service) {
        schoolService = service;
    }

    @Override
    protected SchoolService getService() {
        return schoolService;
    }

    public SchoolServlet() {
        schoolService = new SchoolServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String service = req.getServletPath();
        String path = req.getPathInfo();
        LOGGER.info("仪表盘GET请求[ SERVLET: {} ][ PATH: {} ]", service, path);
        try {
            if ("/delete".equals(path)) {
                super.writerJson(resp, RestfulResult.successful(schoolService.deleteSchool(this.mappingGetParameter(req, School.class))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String service = req.getServletPath();
        String path = req.getPathInfo();
        LOGGER.info("仪表盘GET请求[ SERVLET: {} ][ PATH: {} ]", service, path);
        try {
            if ("/list".equals(path)) {
                final Pagination<?> pagination = mappingPostParameter(req, Pagination.class);
                School school = new School();
                if (pagination.getData() instanceof Map) {
                    final Map data = (Map) pagination.getData();
                    school.setId((Long) data.get("id"));
                    school.setName(data.get("name").toString());
                    school.setAddress(data.get("address").toString());
                    school.setZipCode(data.get("zipCode").toString());
                }
                super.writerJson(resp, RestfulResult.successful(schoolService.list(school, pagination.getCurrent().getPage(), pagination.getCurrent().getSize())));
            } else if ("/save".equals(path)) {
                School school = this.mappingPostParameter(req, School.class);
                super.writerJson(resp, RestfulResult.successful(schoolService.save(school)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
