
package org.humanResources;


import org.humanResources.common.BaseTest;
import org.humanResources.environment.BaseTestEnvironmentImpl;
import org.humanResources.security.entity.AccountImpl;
import org.humanResources.security.entity.AccountQueryFilter;
import org.humanResources.security.service.AccountService;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest extends BaseTest {



    @Autowired
    @Qualifier("accountService")
    AccountService accountService;

	@Autowired
	BaseTestEnvironmentImpl baseTestEnvironment;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		//BaseTest.setUpClass();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("Did setup.");
		super.setUp();
		
		
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAccounts() throws Exception {

        baseTestEnvironment.build();

        AccountImpl account = baseTestEnvironment.getAccounts().get(BaseTestEnvironmentImpl.User_defaultUser);

        //Page<AccountImpl> accounts = accountService.findByNameStartsWith("default");

        final PageRequest page = new PageRequest(0, 20);

        AccountQueryFilter accountQueryFilter = new AccountQueryFilter();
        accountQueryFilter.setName(BaseTestEnvironmentImpl.User_defaultUser);

        Page<AccountImpl> accounts = accountService.findByFilter(accountQueryFilter,page);

        assertThat(accounts).size().isEqualTo(1);


        assertThat(accounts.getContent().get(0).getRoles().size()).isEqualTo(1);


	}


    @Test
    public void testUpdateNameAccount() throws Exception {

        baseTestEnvironment.build();

        String newName = "defaultUser2";

        AccountImpl account = baseTestEnvironment.getAccounts().get(BaseTestEnvironmentImpl.User_defaultUser);

        account = accountService.findById(account.getId());

        assertThat(account).isNotNull();

        account.setName(newName);

        accountService.save(account);

        account = accountService.findById(account.getId());


        assertThat(account.getName()).isEqualTo(newName);

    }




}
