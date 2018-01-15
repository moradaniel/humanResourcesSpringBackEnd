package org.humanResources.environment;


import org.humanResources.security.entity.*;
import org.humanResources.security.service.AccountService;
import org.humanResources.security.service.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public  class BaseTestEnvironmentImpl /*implements BaseTestEnvironment*/{

	AccountService accountService;

	RoleService roleService;


	Map<String, AccountImpl> accounts = null;
	Map<String, RoleImpl> roles = null;


	//roles
	public static final String Role_SYS_ADMIN = "SYS_ADMIN";

	//accounts
	public static final String User_defaultUser = "defaultUser";

	public static final String defaultPassword = "password123";



	public BaseTestEnvironmentImpl(AccountService accountService, RoleService roleService){

		this.accountService = accountService;
		this.roleService = roleService;

	}

	//@Override
	public void build(boolean persist) throws Exception {

		accounts = new HashMap<>();

		roles = new HashMap<>();


		populateRoles();

		populateAccounts();

		if(persist) {
			this.persist();
		}

	}


	private void setDefaultUserForTests() {

		//applicationEventsPublisher.publishEvent(new CurrentUserChangedEvent(users.get(User_defaultUser)));

	}



	private void populateRoles() {

		//1	SYS_ADMIN	Super Role with all permissions, including system-level permissions	1	0
		//2	DEPARTMENT_RESPONSIBLE	responsible of a single node department	1	1
		//3	DEPARTMENTS_SUPERVISOR	view all departments in the entire organization tree	1	2
		//5	HR_MANAGER	Human resources management	1	4


		//create a defaultuser
		RoleImpl SYS_ADMIN_Role = RoleBuilder.aRole()
				.withName(Role_SYS_ADMIN)
                .withDescription("Super Role with all permissions, including system-level permissions")
                .withSortOrder(0)
                .withEnabled(Boolean.TRUE.booleanValue())
				.build();

 //       SYS_ADMIN_Role = (RoleImpl)roleService.save(SYS_ADMIN_Role);

		roles.put(Role_SYS_ADMIN, SYS_ADMIN_Role);

	}




	private void populateAccounts() {

		//create a defaultuser
		AccountImpl defaultUser = AccountBuilder.anAccount()
				.withName(User_defaultUser)
				/*.withUserName(User_defaultUser)*/
				.withPassword(defaultPassword)

				.build();

        RoleImpl adminRole = (RoleImpl) getRoles().get(Role_SYS_ADMIN);
        AccountRoleAssociation association = new AccountRoleAssociation();
        association.setAccount(defaultUser);
        association.setRole(adminRole);
        /*association.setAccountId(this.getId());
        association.setRoleId(role.getId());*/
        association.setSortOrder(0);
        adminRole.addAccount(association);

		defaultUser.addRole(association);

//        defaultUser = accountService.save(defaultUser);

		//userDao.updatePassWord(defaultUser, defaultUser.getPassword());

		accounts.put(User_defaultUser, defaultUser);

	}


	private void persist(){
		
		/*List<Account> result5 = accounts.values().stream()
								.collect(Collectors.toList());
				
		Arrays.stream(values)
	      .mapToObj(i -> Integer.toUnsignedString(i, 16))
	      .forEach(System.out::println);
		*/
		
		roles.values().stream()
			.forEach(roleService::save);
		
		accounts.values().stream()
				.forEach(accountService::save);
				
	}


	//@Override
	public AccountService getAccountService() {
		return accountService;
	}


	//@Override
	public Map<String, AccountImpl> getAccounts(){
		return this.accounts;
	}

	//@Override
	public Map<String, RoleImpl> getRoles(){
		return this.roles;
	}

}