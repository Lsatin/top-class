package com.lsatin.topclass.webspring.servlet;

import com.lsatin.topclass.basic.pojo.Pagination;
import com.lsatin.topclass.basic.pojo.RestfulResult;
import com.lsatin.topclass.webspring.basic.servlet.BasicServlet;
import com.lsatin.topclass.webspring.pojo.School;
import com.lsatin.topclass.webspring.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 学校servlet
 */
public class SchoolServlet extends BasicServlet<School, SchoolService> {

    private SchoolService schoolService;

//    @Autowired
    @Override
    public void setService(SchoolService service) {
        schoolService = service;
    }

    @Override
    public SchoolService getService() {
        return schoolService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servicePath = req.getServletPath();
        LOGGER.info("学校POST请求[ SERVLET: {} ]", servicePath);
        try {
            if ("/delete".equals(servicePath)) {
                super.writerJson(resp, RestfulResult.successful(getService().deleteSchool(this.mappingGetParameter(req, School.class))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String servicePath = req.getServletPath();
        LOGGER.info("学校POST请求[ SERVLET: {} ]", servicePath);
        School school;
        try {
            switch (servicePath) {
                case "/school/list":
                    final Pagination<?> pagination = mappingPostParameter(req, Pagination.class);
                    school = new School();
                    if (pagination.getData() instanceof Map) {
                        final Map<?, ?> data = (Map<?, ?>) pagination.getData();
                        school.setId((Long) data.get("id"));
                        school.setName(data.get("name").toString());
                        school.setAddress(data.get("address").toString());
                        school.setZipCode(data.get("zipCode").toString());
                    }
                    super.writerJson(resp, RestfulResult.successful(getService().list(school, pagination.getCurrent().getPage(), pagination.getCurrent().getSize())));
                    break;
                case "/school/save":
                    school = this.mappingPostParameter(req, School.class);
                    super.writerJson(resp, RestfulResult.successful(getService().save(school)));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
