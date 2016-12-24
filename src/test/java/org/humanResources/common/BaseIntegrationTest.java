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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import static org.powermock.api.mockito.PowerMockito.whenNew;


//http://www.jayway.com/2009/10/28/untestable-code-with-mockito-and-powermock/

//@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)


//uncomment WebIntegrationTest to use a real container(Eg Tomcat)
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

//Classes that will be prepared by powermock
/*@PrepareForTest({ RsUser.class, RsUtil.class})
@PowerMockIgnore({"javax.management.*",
                  "javax.crypto.*",
                  "javax.net.ssl.*",
                  "org.springframework.orm.*",
                  "org.hibernate.*"})*/

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
/*
    @Autowired
   // @Qualifier("rsmetaDataSource")
    DataSource dataSource;


    @Autowired
    @Qualifier("webServicesObjectMapper")
    protected ObjectMapper webServicesObjectMapper;

    @Autowired
    @Qualifier("domainObjectMapper")
    protected ObjectMapper domainObjectMapper;

*/
    protected String contentType = MediaType.APPLICATION_JSON_UTF8_VALUE;//"application/json";

  //  protected String resultContentType = "application/json-rpc";

    
    /*protected static class Application {
		public static void main(String[] args) {
			new SpringApplicationBuilder(com.rs.web.Application.class).properties(
					"spring.application.name=eureka", "server.servletPath=/servlet").run(
					args);
		}
	}*/
    
    /*
    @Autowired
    //@Qualifier("myDataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        System.out.println(dataSource);
    }*/

    /*
    @Autowired
    DatabasePopulator databasePopulator;*/

    static {
    	//Alternatively we can set -Dspring.config.location=classpath:rsapp20-jsonrpc/ as a VM parameter
        System.setProperty("spring.config.location", "classpath:"+SpringBootHumanResourcesApplication.classpathBaseDirectory+"/");

        /*rsconfig = (PropertyResourceBundle)PropertyResourceBundle.getBundle("rs_config");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));*/

/*
        DatabaseBean.setJ2SEpool((String)rsconfig.handleGetObject("jdbcDriver"),
                (String)rsconfig.handleGetObject("dbConnectString"),
                (String)rsconfig.handleGetObject("dbConnectStringRsd")
        );

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

        //clean test database bebore each test
    //    ScriptUtils.executeSqlScript(dataSource.getConnection(),
    //            new ClassPathResource("sql/clean_test_database.sql"));

        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));

        Date NOW = sdf.parse("2015-03-15 00:00:00");
        long NOWinMillis=NOW.getTime();
        //final Date mockedDate = mock(Date.class);
        mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(NOWinMillis);*/


        //expect(System.getProperty("property")).andReturn("my property");

        //replayAll();
        /*
            everytime we call new Date() in any class declared in @PrepareForTest
            we will get the NOW instance
        * */
        //whenNew(Date.class).withNoArguments().thenReturn(mockedDate);


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