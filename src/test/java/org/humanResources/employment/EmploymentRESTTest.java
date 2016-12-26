package org.humanResources.employment;

//import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import org.humanResources.common.BaseIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.powermock.api.mockito.PowerMockito.whenNew;


//@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebIntegrationTest

//@ActiveProfiles(profiles= { "integrationTest" })


/*@PrepareForTest({ RsUser.class })
@PowerMockIgnore("javax.management.*")*/
public class EmploymentRESTTest extends BaseIntegrationTest{



    //private static String URI = "/api/v1/PropertyService.json";

    /*
	@Autowired
	BaseTestEnvironmentImpl baseTestEnvironment;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    private SensorInZoneService sensorInZoneService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private SensorService sensorService;



    @Autowired
    protected WebServiceConnector webServiceConnector;
*/

    @Before
    public void setUp() throws Exception {
        logger.info("Setting up the test");
        super.setUp();


    }

   /* @Test
    public void test_PropStat_Normal_Request() throws Exception {


    //	baseTestEnvironment.build();

        String contentType = "application/json";

        String resultContentType = "application/json-rpc";

     //   JsonNode propstatJsonRequest = getJSonFromFile("propertyService/propStat/01_jsonRpcPropertyStatus_Normal_RequestTest.json");

     //   JsonNode expectedResponseJson = getJSonFromFile("propertyService/propStat/01_jsonRpcPropertyStatus_Normal_ExpectedResponseTest.json");

     //   String validStatelessToken = loginWithValidUser();



        MvcResult result = this.mockMvc.perform(
                post(URI)
                        .contentType(MediaType.valueOf(contentType))
                        .accept(MediaType.valueOf(contentType))
                     //   .header("X-AUTH-TOKEN", validStatelessToken) //add token to request
                        .content(propstatJsonRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(resultContentType))

                // TODO check the received json is equal to expected
                //.andExpect(content().json(expectedResponseJson.toString()))
                
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);

    }*/

    @Test
    public void getAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/employment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
              //  .andExpect(jsonPath("$",iterableWithSize(5)))
              //  .andExpect(jsonPath("$[0]['title']",containsString(SPRING_BOOT_MATCH)));
                .andReturn();

        String content = result.getResponse().getContentAsString();
        logger.info(content);

    }


    //@Test
    /*public void test() {
        this.restTemplate.getForEntity("/{username}/vehicle",
                String.class, "sframework");
    }*/


}