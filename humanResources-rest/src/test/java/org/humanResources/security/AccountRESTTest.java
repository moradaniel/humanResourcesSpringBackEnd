package org.humanResources.security;

//import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import org.humanResources.common.BaseIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountRESTTest extends BaseIntegrationTest{



    private static String baseURL = "/api/account";


    @Before
    public void setUp() throws Exception {
        logger.info("Setting up the test");
        super.setUp();


    }

    @Test
    public void getAll() throws Exception {
        MvcResult result = mockMvc.perform(get(baseURL+"/findByNameStartsWith").param("name","default"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
              //  .andExpect(jsonPath("$",iterableWithSize(5)))
              //  .andExpect(jsonPath("$[0]['title']",containsString(SPRING_BOOT_MATCH)));
                .andReturn();

        String content = result.getResponse().getContentAsString();
        logger.info(content);

    }


}