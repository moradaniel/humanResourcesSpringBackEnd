package org.humanResources.web;

import org.humanResources.json.WebServicesObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Properties;

//Tags this class as a source of bean definitions for the application context.
@Configuration

/*
tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
  Normally you would add @EnableWebMvc for a Spring MVC app,
  but Spring Boot adds it automatically when it sees
  spring-webmvc on the classpath.
  This flags the application as a web application
  and activates key behaviors such as setting up a DispatcherServlet.
 */
@EnableAutoConfiguration

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

@SpringBootApplication
@ComponentScan(basePackages = {"org.humanResources"})
@EnableJpaRepositories("org.humanResources")
@EntityScan("org.humanResources")
public class SpringBootHumanResourcesApplication extends SpringBootServletInitializer {

    final public static String classpathBaseDirectory = "humanResources";

    @Autowired
    @Qualifier("webServicesObjectMapper")
    WebServicesObjectMapper webServicesObjectMapper;

    static Properties getProperties() {


        Properties props = new Properties();
        props.put("spring.config.location", "classpath:"+classpathBaseDirectory+"/");

        return props;
    }

    //Executed when deployed as a war file
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
        checkExistingApplicationProfiles();



        return springApplicationBuilder.sources(SpringBootHumanResourcesApplication.class)
                .properties(getProperties());
    }

    private static void checkExistingApplicationProfiles() {

        String activeProfiles = System.getProperty("spring.profiles.active");

        boolean validSpringProfiles = true;
        if(StringUtils.isEmpty(activeProfiles)){
            validSpringProfiles = false;
        }

        if (validSpringProfiles == false){

            StringBuffer errorBuf = new StringBuffer();

            errorBuf.append("\n########################################################################################## \n");
            errorBuf.append("#############   SEVERE ERROR. Attempting to start humanResources application   ########### \n");
            errorBuf.append("#############   Please configure 'spring.profiles.active' VM environment variable.  ####### \n");
            errorBuf.append("#############   For example:                                       ###################### \n");
            errorBuf.append("#############    -Dspring.profiles.active=production,company1      ###################### \n");
            errorBuf.append("#############    -Dspring.profiles.active=production,company2        ###################### \n");
            errorBuf.append("########################################################################################## \n");

            throw new RuntimeException(errorBuf.toString());

        }else{
            System.out.println("##########################################################################################");
            System.out.println("#############   Running with 'spring.profiles.active' values :      ######################");
            System.out.println("#############   -Dspring.profiles.active="+activeProfiles+"         ######################");
            System.out.println("##########################################################################################");
        }


    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        //ObjectMapper objectMapper = new ObjectMapper();
        //webServicesObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(webServicesObjectMapper);
        return jsonConverter;
    }


    //Executed when run as a standalone application (jar file)
    public static void main(String[] args) {
        //ApplicationContext ctx = SpringApplication.run(Application.class, args);


        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(SpringBootHumanResourcesApplication.class)
                .sources(SpringBootHumanResourcesApplication.class)
                .properties(getProperties());

        ApplicationContext ctx = springApplicationBuilder.run(args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}