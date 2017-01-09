package org.humanResources.security.service;


import com.querydsl.core.types.Predicate;
import org.humanResources.security.entity.AccountImpl;
import org.humanResources.security.entity.AccountPredicates;
import org.humanResources.security.entity.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

//@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    AccountRepository accountRepository;



    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    @Transactional
    public Page<AccountImpl> findByNameStartsWith(String name){

  /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/
        final PageRequest page1 = new PageRequest(0, 20);

        Predicate accountPredicate = AccountPredicates.firstOrLastNameStartsWith(name);
        Page<AccountImpl> accounts = accountRepository.findAll(accountPredicate,page1);

        return accounts;
    }

    @Transactional
    public AccountImpl findById(Long id){

        AccountImpl account = accountRepository.findOne(id);

        return account;
    }

    /*
    @Transactional
    public Page<Account> findAll(){*/

        /*
        final PageRequest page1 = new PageRequest(
                0, 20, Direction.ASC, "lastName", "salary"
        );

        final PageRequest page2 = new PageRequest(
                0, 20, new Sort(
                new Order(Direction.ASC, "lastName"),
                new Order(Direction.DESC, "salary")
        )
        );

        */

     /*   Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findAll();

        return accounts;
    }*/


    @Transactional
    public AccountImpl save(AccountImpl account){

        account = this.accountRepository.save(account);
        return account;
    }
}