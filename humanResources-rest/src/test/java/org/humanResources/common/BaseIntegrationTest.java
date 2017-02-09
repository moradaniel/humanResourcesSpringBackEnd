package org.humanResources.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.humanResources.config.IntegrationTestsWebServicesConfig;
import org.humanResources.environment.BaseTestEnvironmentImpl;
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
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)


@SpringBootTest(classes = {SpringBootHumanResourcesApplication.class,

        IntegrationTestsWebServicesConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public abstract class BaseIntegrationTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    protected Environment env;


    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;


    protected String contentType = MediaType.APPLICATION_JSON_UTF8_VALUE;//"application/json";

  //  protected String resultContentType = "application/json-rpc";

    
    static {
    	//Alternatively we can set -Dspring.config.location=classpath:humanResources/ as a VM parameter
        System.setProperty("spring.config.location", "classpath:"+SpringBootHumanResourcesApplication.classpathBaseDirectory+"/");

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

        //clean test database before each test

        emptyDatabaseSchema();

        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("humanResources/sql/clean_test_database.sql"));

  /*      ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("humanResources/sql/populate_test_data.sql"));

*/

        this.mockMvc = webAppContextSetup(webApplicationContext)
                //@Autowire the FilterChainProxy (what the DelegatingProxyFilter delegates to)
                //and instruct MockMvc to use the FilterChainProxy.
                .addFilters(this.springSecurityFilterChain)
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

    @After
    public void tearDown() throws Exception {
        /*ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("sql/after_test_database.sql"));*/
    }


    protected String loginWithUser(String userName){
        return getStatelessJWTSecurityToken(userName, BaseTestEnvironmentImpl.defaultPassword);
    }

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    protected String getStatelessJWTSecurityToken(String userName, String password) {


        String contextPath = env.getProperty("server.contextPath");
        String port = env.getProperty("server.port");
        String strToken = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse httpResponse = null;

        try {

            HttpPost request = new HttpPost("http://localhost:" + port + contextPath+"/api/auth/login");
            StringEntity params = new StringEntity("{\"username\":\"" + userName + "\",\"password\":\"" + password + "\"}");
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "application/json");
            request.addHeader("X-Requested-With", "XMLHttpRequest");

            request.setEntity(params);

            httpResponse = httpclient.execute(request);

            System.out.println(httpResponse.getStatusLine());

            org.apache.http.HttpEntity responseEntity = httpResponse.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            //Header header = httpResponse.getFirstHeader("X-AUTH-TOKEN");
            //strToken = header.getValue();

            String responseBody = EntityUtils.toString(httpResponse.getEntity());

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String,String>> typeRef
                    = new TypeReference<HashMap<String,String>>() {};

            HashMap<String,String> responseJson = mapper.readValue(responseBody, typeRef);

            strToken = responseJson.get("token");

            System.out.println(strToken);
            EntityUtils.consume(responseEntity);

            return strToken;
        } catch (Exception ex) {

            throw new RuntimeException(ex);
        } finally {
            try {if (httpResponse != null) httpResponse.close();} catch (Exception e) {	throw new RuntimeException(e);}
        }
    }


}