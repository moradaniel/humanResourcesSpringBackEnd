package org.humanResources.security.web;

import org.humanResources.security.entity.AccountImpl;
import org.humanResources.security.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;


    @RequestMapping(value="/api/account/findByNameStartsWith",
                    method = RequestMethod.GET,
                    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody Page<AccountImpl> findByNameStartsWith(@RequestParam("name") String name){
        return accountService.findByNameStartsWith(name);
    }



}