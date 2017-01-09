package org.humanResources.employment.service;

import org.humanResources.employment.model.Employment;
import org.humanResources.employment.repository.EmploymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service
public class EmploymentService {

    private static final Logger log = LoggerFactory.getLogger(EmploymentService.class);

    //@Autowired
    EmploymentRepository employmentRepository;

    public EmploymentService(EmploymentRepository employmentRepository){
      this.employmentRepository = employmentRepository;
    }



    @Transactional
    public List<Employment> findAll(){
        List<Employment> employments = employmentRepository.findAll();
        return employments;
    }
}