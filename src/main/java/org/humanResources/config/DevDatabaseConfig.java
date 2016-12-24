package org.humanResources.config;


import oracle.jdbc.pool.OracleDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DevDatabaseConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "oracleDataSource")
    DataSource oracleDataSource() throws SQLException {


        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(env.getProperty("oracle.username"));
        dataSource.setPassword(env.getProperty("oracle.password"));
        dataSource.setURL(env.getProperty("oracle.url"));
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }


    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                //setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(@Qualifier("oracleDataSource") DataSource oracleDataSource/*,
                                                  @Qualifier("timeProvider") TimeProvider timeProvider*/) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(oracleDataSource);
        sessionFactory.setHibernateProperties(hibernateProperties());

        /*
        String[] mappingResources = new String[] {
                "hibernate/model/User.hbm.xml",
                "hibernate/model/Dashboard.hbm.xml",
                "hibernate/model/Widget.hbm.xml",
                "hibernate/model/WidgetType.hbm.xml"
        };*/
        /*String[] mappingResources = new String[] {
                "hibernate/model/User.hbm.xml",
                "hibernate/model/PowerAgency.hbm.xml",
                "hibernate/model/Unit.hbm.xml",
                "hibernate/model/UnitGroup.hbm.xml",
                "hibernate/model/LoadControlSchedule.hbm.xml",
                "hibernate/model/LoadControlEvent.hbm.xml",
                "hibernate/model/LoadControlUnitGroup.hbm.xml"

        };


        sessionFactory.setMappingResources(mappingResources);*/

        /*AuditInterceptor auditInterceptor = new AuditInterceptor();
        auditInterceptor.setTimeProvider(timeProvider);
        //auditInterceptor.setUserService(userService);

        sessionFactory.setEntityInterceptor(auditInterceptor);*/
        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}
