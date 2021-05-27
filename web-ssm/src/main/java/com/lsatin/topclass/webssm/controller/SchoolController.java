package com.lsatin.topclass.webssm.controller;

import com.lsatin.topclass.basic.pojo.Pagination;
import com.lsatin.topclass.basic.pojo.RestfulResult;
import com.lsatin.topclass.webssm.pojo.School;
import com.lsatin.topclass.webssm.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    private SchoolService schoolService;

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestfulResult<School> save(@RequestBody School school) {
        return RestfulResult.successful(school);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestfulResult<Pagination<List<School>>> list(@RequestBody Pagination<School> pagination) {
        return RestfulResult.successful(schoolService.list(pagination));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(Long id) {
        School school = new School();
        school.setId(id);
        schoolService.deleteSchool(school);
    }
}
