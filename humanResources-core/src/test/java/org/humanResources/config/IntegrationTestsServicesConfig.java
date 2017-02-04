package org.humanResources.config;


import org.humanResources.environment.BaseTestEnvironmentImpl;
import org.humanResources.security.service.AccountService;
import org.humanResources.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integrationTest")
public class IntegrationTestsServicesConfig {

    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    @Autowired
    @Qualifier("roleService")
    private RoleService roleService;

    @Bean
    public BaseTestEnvironmentImpl testEnvironment() {
        BaseTestEnvironmentImpl testEnvironment = new BaseTestEnvironmentImpl(
    			accountService,
                roleService
    			);

        return testEnvironment;
    }



}