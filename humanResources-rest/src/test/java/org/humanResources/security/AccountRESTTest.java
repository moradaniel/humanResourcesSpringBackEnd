package org.humanResources.security;

//import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import org.humanResources.common.BaseIntegrationTest;
import org.humanResources.environment.BaseTestEnvironmentImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountRESTTest extends BaseIntegrationTest{



    @Autowired
    BaseTestEnvironmentImpl baseTestEnvironment;

    private static String baseURL = "/api/account";


    @Before
    public void setUp() throws Exception {
        logger.info("Setting up the test");
        super.setUp();


    }

    @Test
    public void getAll() throws Exception {

        baseTestEnvironment.build();

        String validStatelessToken = loginWithUser(baseTestEnvironment.getAccounts().get(BaseTestEnvironmentImpl.User_defaultUser).getName());

        MvcResult result = mockMvc.perform(
                get(baseURL+"/findByFilter").param("name","defaultUser")
                .contentType(MediaType.valueOf(contentType))
                .accept(MediaType.valueOf(contentType))
                .header("X-Authorization", "Bearer "+validStatelessToken) //add token to request
                // .header("X-AUTH-TOKEN", validStatelessToken) //add token to request

                /*.content(loadUserJsonRequest.toString())*/)

                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
              //  .andExpect(jsonPath("$",iterableWithSize(5)))
              //  .andExpect(jsonPath("$[0]['title']",containsString(SPRING_BOOT_MATCH)));
                .andReturn();

        String content = result.getResponse().getContentAsString();
        logger.info(content);

    }


}