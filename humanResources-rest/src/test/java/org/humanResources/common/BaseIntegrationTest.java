package org.humanResources.common;

import org.humanResources.config.IntegrationTestsWebServicesConfig;
import org.humanResources.web.SpringBootHumanResourcesApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



//http://www.jayway.com/2009/10/28/untestable-code-with-mockito-and-powermock/

@RunWith(SpringJUnit4ClassRunner.class)


@SpringBootTest(classes = {SpringBootHumanResourcesApplication.class,
        /*IntegrationTestsDatabaseConfig.class,
        DevDatabaseConfig.class,
        IntegrationTestsServicesConfig.class,*/
        IntegrationTestsWebServicesConfig.class})
/*@SpringBootTest(classes = {com.rs.web.Application.class,
        IntegrationTestsDatabaseConfig.class,
		DevDatabaseConfig.class,
        IntegrationTestsServicesConfig.class,
        IntegrationTestsWebServicesConfig.class})*/


//@ActiveProfiles(profiles= { "integrationTest" })

/*@ContextConfiguration(classes = { IntegrationTestsServicesConfig.class,
                                 IntegrationTestsDatabaseConfig.class})
@ContextConfiguration(classes={ IntegrationTestsServicesConfig.class,
        IntegrationTestsDatabaseConfig.class}, loader = SpringApplicationContextLoader.class)*/



public abstract class BaseIntegrationTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

   // private static BasicDataSource dbDataSource;
    //private static PropertyResourceBundle rsconfig;


    @Autowired
    protected Environment env;


    //@Autowired
    //private FilterChainProxy springSecurityFilterChain;

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

/*
    @Autowired
    protected TestRestTemplate restTemplate;*/

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

/*
    @Autowired
    @Qualifier("webServicesObjectMapper")
    protected ObjectMapper webServicesObjectMapper;

    @Autowired
    @Qualifier("domainObjectMapper")
    protected ObjectMapper domainObjectMapper;

*/
    protected String contentType = MediaType.APPLICATION_JSON_UTF8_VALUE;//"application/json";

  //  protected String resultContentType = "application/json-rpc";

    
    static {
    	//Alternatively we can set -Dspring.config.location=classpath:rsapp20-jsonrpc/ as a VM parameter
        System.setProperty("spring.config.location", "classpath:"+SpringBootHumanResourcesApplication.classpathBaseDirectory+"/");

/*
        dbDataSource = new BasicDataSource();


        dbDataSource.setDriverClassName((String) rsconfig.handleGetObject("jdbcDriver"));
        dbDataSource.setUrl((String) rsconfig.handleGetObject("dbConnectString"));

        dbDataSource.setMaxActive(30);
        dbDataSource.setMaxWait(30000);//wait 30 seconds for available connections*/
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //create test database only once
        try {
            /*ScriptUtils.executeSqlScript(
                    dbDataSource.getConnection(),
                    new ClassPathResource("sql/rsss_schema.sql"));*/

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Before
    public void setUp() throws Exception {

        injectStaticTimeProvider();
        injectStaticObjectMapper();
        //create database before each test
        //DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        //clean test database before each test

        emptyDatabaseSchema();

        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("humanResources/sql/populate_test_data.sql"));



        this.mockMvc = webAppContextSetup(webApplicationContext)
                //@Autowire the FilterChainProxy (what the DelegatingProxyFilter delegates to)
                //and instruct MockMvc to use the FilterChainProxy.
               // .addFilters(this.springSecurityFilterChain)
                .build();

/*
        // Mocking email server
        final Properties props = new Properties();
        if (logger.isDebugEnabled()) {
            props.setProperty("mail.debug", "true");
        }
        props.setProperty("mail.transport.protocol.rfc822", "mock_smtp");
        Session session = Session.getInstance(props);

        final Transport mockedTransport = session.getTransport(Providers.getSMTPProvider("makes_no_difference_here", true, true));


        //mocking session.getTransport(String protocol)
        //Transport tr = session.getTransport("smtp");
        //when(Session.class,"getTransport","smtp").thenReturn(mockedTransport);
        //final String expectedReturnValue = "new";
        stub(method(Session.class, "getTransport")).toReturn(mockedTransport);*/

    }

    private void emptyDatabaseSchema() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ClassPathResource classPathResource = new ClassPathResource("humanResources/sql/empty_database_schema.plsql");
            BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()),1024);

            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String plsql = stringBuilder.toString();

        Connection connection = null;
        CallableStatement cstmt = null;

        try
        {
            connection = dataSource.getConnection();
            cstmt = connection.prepareCall(plsql);
            cstmt.execute();


        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally
        {
            try { if (cstmt != null) cstmt.close(); } catch (Exception e) {};
            try { if (connection != null) connection.close(); } catch (Exception e) {};
        }



    }

    private void injectStaticTimeProvider() {

        /*
        TestTimeProvider testTimeProvider= new TestTimeProvider(new Date());
        RsUser.setTimeProvider(testTimeProvider);
        RsUtil.setTimeProvider(testTimeProvider);
        Actuation.setTimeProvider(testTimeProvider);*/


    }

    private void injectStaticObjectMapper() {

       // RsUser.setObjectMapper(domainObjectMapper);

    }

    @After
    public void tearDown() throws Exception {
        /*ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("sql/after_test_database.sql"));*/
    }
/*
    protected JsonNode getJSonFromFile(String jsonFile) {
        JsonNode actualObj;

        try {
            //String jsonString = IOUtils.toString(new ClassPathResource(jsonFile).getInputStream());
            String jsonString = "pepe";
            actualObj = webServicesObjectMapper.readTree(jsonString);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return actualObj;
    }

    protected JsonNode getJSonFromObject(Object object) {
        JsonNode actualObj;
        try {
            //String jsonString = IOUtils.toString(new ClassPathResource(jsonFile).getInputStream());
            //ObjectMapper mapper = new ObjectMapper();
            actualObj = webServicesObjectMapper.valueToTree(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return actualObj;

    }

*/

}