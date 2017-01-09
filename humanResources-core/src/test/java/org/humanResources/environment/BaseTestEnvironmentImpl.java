package org.humanResources.environment;


import org.humanResources.security.entity.AccountBuilder;
import org.humanResources.security.entity.AccountImpl;
import org.humanResources.security.service.AccountService;

import java.util.HashMap;
import java.util.Map;


public  class BaseTestEnvironmentImpl /*implements BaseTestEnvironment*/{

	AccountService accountService;

	Map<String, AccountImpl> accounts = null;

	public static final String User_defaultUser = "defaultUser";

	public static final String defaultPassword = "password123";



	public BaseTestEnvironmentImpl(AccountService accountService){

		this.accountService = accountService;

	}

	//@Override
	public void build() throws Exception {

		accounts = new HashMap<String,AccountImpl>();


		populateAccounts();



	}


	private void setDefaultUserForTests() {

		//applicationEventsPublisher.publishEvent(new CurrentUserChangedEvent(users.get(User_defaultUser)));

	}







	private void populateAccounts() {

		//create a defaultuser
		AccountImpl defaultUser = AccountBuilder.anAccount()
				.withName(User_defaultUser)
				/*.withUserName(User_defaultUser)*/
				.withPassword(defaultPassword)

				.build();

		accountService.save(defaultUser);

		//userDao.updatePassWord(defaultUser, defaultUser.getPassword());

		accounts.put(User_defaultUser, defaultUser);

	}





	//@Override
	public AccountService getAccountService() {
		return accountService;
	}


	//@Override
	public Map<String, AccountImpl> getAccounts(){
		return this.accounts;
	}

}