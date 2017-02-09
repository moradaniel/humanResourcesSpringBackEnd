package org.humanResources.security.service;

//import org.janux.bus.security.*;

import org.humanResources.security.entity.AccountImpl;
import org.humanResources.security.entity.AccountQueryFilter;
import org.humanResources.security.entity.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 ***************************************************************************************************
 * Adapter class that implements a spring security UserDetailsService for authentication; implements
 * the single UserDetailsService method by delegating to 
 * {@link AccountDaoGeneric#findByName(String username)}
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 2012-03-28
 ***************************************************************************************************
 */
public class JanuxUserDetailsService implements UserDetailsService {
	//private AccountDaoGeneric<AccountImpl> accountDao;

	AccountService accountService;

	public JanuxUserDetailsService(AccountService accountService) {
		this.accountService = accountService;
	}

	/** The account dao to which this UserDetailsService delegates */
  //public AccountDaoGeneric<Account> getAccountDao() { return accountDao;}

  //public void setAccountDao(AccountDaoGeneric<Account> o) { accountDao = o; }

	public UserDetails loadUserByUsername(String username) 
	{
		AccountQueryFilter filter = new AccountQueryFilter();
		filter.setName(username);

		final PageRequest page = new PageRequest(0, 1);


		Page<AccountImpl> accounts = accountService.findByFilter(filter,page);

		if (accounts.getTotalElements() == 1) {
			return new JanuxUserDetails(accounts.getContent().get(0));
		}
		else {
			return null;
		}
	}

} // end class JanuxUserDetailsService
