package org.humanResources.config;

import org.humanResources.employment.repository.EmploymentRepository;
import org.humanResources.employment.service.EmploymentService;
import org.humanResources.security.entity.AccountRepository;
import org.humanResources.security.entity.RoleRepository;
import org.humanResources.security.service.AccountService;
import org.humanResources.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;


@Configuration
@EnableAsync
@EnableJpaRepositories(basePackages="org.humanResources",
                       entityManagerFactoryRef="entityManagerFactory"/*,
                       repositoryFactoryBeanClass=JoinFetchCapableQueryDslJpaRepositoryFactoryBean.class
                       repositoryBaseClass=QueryDslJpaEnhancedRepositoryImpl.class,
                       basePackageClasses=SomeRepository.class*/)


@Profile("humanResources")
public class ServicesConfig {



    @Autowired
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @Autowired
    @Qualifier("roleRepository")
    private RoleRepository roleRepository;


    @Autowired
    @Qualifier("employmentRepository")
    private EmploymentRepository employmentRepository;


    @Bean(name="accountService")
    public AccountService accountService() {
        AccountService accountService = new AccountService(accountRepository);
        return accountService;
    }

    @Bean(name="roleService")
    public RoleService roleService() {
        RoleService roleService = new RoleService(roleRepository);
        return roleService;
    }


    @Bean(name="employmentService")
    public EmploymentService employmentService() {
        EmploymentService employmentService = new EmploymentService(employmentRepository);
        return employmentService;
    }


      @PostConstruct
    public void resolveCyclicDependencies() {

    }




}
