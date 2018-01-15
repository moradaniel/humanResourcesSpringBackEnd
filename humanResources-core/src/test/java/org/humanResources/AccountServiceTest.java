package org.humanResources;

import org.humanResources.security.entity.Account;
import org.humanResources.security.entity.AccountBuilder;
import org.humanResources.security.entity.AccountQueryFilter;
import org.humanResources.security.entity.AccountRepository;
import org.humanResources.security.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;


/**
 * Unit Test for the AccountRepository service
 * The AccountRepository is a mock
 * No spring infrastructure is initialized
 *  
 *  Book: Beginning Spring Boot 2
 *  Chapter 15 Testing Spring Boot Applications
 * 
 */

@RunWith(MockitoJUnitRunner.class)
    public class AccountServiceTest
    {
        @Mock
        private AccountRepository accountRepository;

        @InjectMocks
        private AccountService accountService;

        @Test
        public void should_retrieve_accounts_when_calling_findByFilter() {

            List<Account> accounts = new ArrayList<>();
            Account account1 = AccountBuilder.anAccount()
                                    .withName("account1")
                                    .withPassword("passaccount1")
                                    .build();

            Account account2 = AccountBuilder.anAccount()
                    .withName("account2")
                    .withPassword("passaccount2")
                    .build();

            accounts.add(account1);
            accounts.add(account2);

            Page<Account> page = new PageImpl(accounts);

            BDDMockito.<Page<Account>>given(accountRepository.findByFilter(any(AccountQueryFilter.class),any(Pageable.class)))
                                        .willReturn(page);

            final PageRequest pageable = new PageRequest(0, 20);

            Page<Account> result = accountService.findByFilter(new AccountQueryFilter(), pageable );

            assertThat(result.getTotalElements()).isEqualTo(2);

        }
    }