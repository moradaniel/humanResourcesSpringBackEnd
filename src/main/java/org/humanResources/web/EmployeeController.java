package org.humanResources.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController
{
    @RequestMapping("/")
    //public List<Employee> getEmployees()
    public String getEmployees()
    {
        /*List<Employee> employeesList = new ArrayList<Employee>();
        employeesList.add(new Employee(1,"test","test","test@gmail.com"));
        return employeesList;

        */
        return "hi";
    }
}