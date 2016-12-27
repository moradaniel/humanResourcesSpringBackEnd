package org.humanResources.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@Profile("integrationTest")
public class IntegrationTestsWebServicesConfig {

    @Autowired
    private Environment env;

    /*
    @Autowired
    @Qualifier("webServicesObjectMapper")
    private ObjectMapper webServicesObjectMapper;
    
    @Bean(name = "webServiceConnector")
    public WebServiceConnector webServiceConnector() {
    	WebServiceConnector webServiceConnector = new WebServiceConnector();
    	webServiceConnector.setObjectMapper(webServicesObjectMapper);
    	webServiceConnector.setEnvironment(env);
        return webServiceConnector;
    }*/
    
}