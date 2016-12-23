package org.humanResources.employment.web;

import org.humanResources.employment.model.Employment;
import org.humanResources.employment.service.EmploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmploymentController {

    private static final Logger log = LoggerFactory.getLogger(EmploymentController.class);

    @Autowired
    EmploymentService employmentService;


    @RequestMapping(value="/employment",
                    method = RequestMethod.GET,
                    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody List<Employment> getEmployment(){
        return employmentService.findAll();
    }
}