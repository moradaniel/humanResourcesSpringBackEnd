package org.humanResources.security.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository("accountRepository")
//public	interface	AccountRepository	extends JpaRepository<AccountImpl,	Long>, QueryDslPredicateExecutor<AccountImpl> {
    public interface AccountRepository extends AccountRepositoryCustom, JpaRepository<AccountImpl, Long>, QueryDslPredicateExecutor<AccountImpl>  {


    //	Returns	unique	user	with	given	user-name
    AccountImpl	findByName(String	name);

    /**
     *  Returns	a paginated	list of	users	whose	name	starts	with given value
     */
    //@EntityGraph(attributePaths = { "role" })
    //Page<Account> findByNameStartsWith(String	name, Pageable pageable);

    /**	Returns	first	5	users	whose	name	starts	with	given	value,
    	order	by	name	descending*/

    //List<User> findTop5ByNameStartsWithOrderByNameDesc(String	name);

    /**	Returns	number	of	users	whose	birth	date	is	before	the	given	value
     *
     * @param dob
     * @return
     */
    //Long	countUsersDateOfBirthLessThan(Date dob);

    /**	Deletes	the	User	of	given	id*/
    //void deleteById(Long	userId);

    /*	Asynchronously	returns	a	list	of	users	whose	name	contains	//	the
    given	value
    @Async
    Future<List<User>> findByNameContains(String name);*/
}