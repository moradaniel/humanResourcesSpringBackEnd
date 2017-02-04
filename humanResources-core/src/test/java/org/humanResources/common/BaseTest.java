package org.humanResources.common;

import org.humanResources.config.DevDatabaseConfig;
import org.humanResources.config.IntegrationTestsServicesConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;


@RunWith(SpringJUnit4ClassRunner.class)

//the right ServicesConfig class will be instantiated depending on the active Spring profiles
@ContextConfiguration(classes={
								DevDatabaseConfig.class,
                                org.humanResources.config.ServicesConfig.class,
                                /*,
								IntegrationTestsDatabaseConfig.class,*/
                                IntegrationTestsServicesConfig.class

								},
								loader=AnnotationConfigContextLoader.class)

@TestPropertySource(locations = "classpath:humanResources/application-integrationTest.properties")
public abstract class BaseTest {

	@Autowired
	protected Environment env;

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

/*
	@Autowired
	@Qualifier("domainObjectMapper")
	protected ObjectMapper domainObjectMapper;

	*/
	static {


	}

	private static boolean initialized = false;

	@Before
	public void initializeDB() {
		if (!initialized) {

			//create test database only once
			try {

              //  emptyDatabaseSchema();
			}catch(Exception e) {
				throw new RuntimeException(e);
			}

			initialized = true;
		}
	}

    private void emptyTestDatabaseSchema() throws Exception {
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


	@Before
	public void setUp() throws Exception {

        emptyTestDatabaseSchema();

        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new ClassPathResource("humanResources/sql/clean_test_database.sql"));

    }


	

}
