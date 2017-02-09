package org.humanResources.security.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


public class AccountRepositoryImpl /*extends QueryDslRepositorySupport*/ implements AccountRepositoryCustom {

    /*public AccountRepositoryImpl() {
        super(AccountImpl.class);
    }*/

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<AccountImpl> findByFilter(AccountQueryFilter accountQueryFilter, Pageable pageable) {
  /*
        org.humanResources.security.entity.QAccountImpl account = org.humanResources.security.entity.QAccountImpl.accountImpl;
        QAccountRoleAssociation accountRoleAssociation = QAccountRoleAssociation.accountRoleAssociation;
        QRoleImpl role = QRoleImpl.roleImpl;

        JPQLQuery query = from(account)
                .leftJoin(account.roles,accountRoleAssociation)
                .leftJoin(account.roles.raccountRoleAssociation.role,role)
                .fetchJoin()
                //.where(QAccountImpl.accountImpl.officeCode.eq(officeCode));
                ;
        query = super.getQuerydsl().applyPagination(pageable, query);
        //QueryResults<AccountImpl> entities = query.fetchResults();
        List<AccountImpl> entities = query.fetch();
        return new PageImpl<AccountImpl>(entities, pageable, entities.size());
*/

        List<String> wheres = new ArrayList<>();
        List<String> paramNames = new ArrayList();
        List<Object> values = new ArrayList();

        String select = " Select distinct account ";
        String countSelect = " Select count(distinct account) ";

        StringBuffer queryBuilder = new StringBuffer();
        //queryBuilder.append(" Select account ");
        queryBuilder.append(" from AccountImpl account ");
        queryBuilder.append(" left join fetch account.roles roles ");
        queryBuilder.append(" inner join fetch roles.role ");
        //queryBuilder.append(" where e.salary " + "Between 30000 and 40000");

        buildWhereClause(accountQueryFilter,wheres,paramNames,values);


        String queryWithoutOrdering = wheres.isEmpty() ? queryBuilder.toString() : queryBuilder.append(" WHERE ").append(
                org.humanResources.util.StringUtils.getStringsSeparatedBy(" AND ", wheres)).toString();


        String queryWithOrdering = queryWithoutOrdering;
        /*if (employmentQueryFilter != null && employmentQueryFilter.getOrderBy() != null) {
            queryWithOrdering += " ORDER BY LOWER(" + employmentQueryFilter.getOrderBy() + ")"
                    + (employmentQueryFilter.getOrderDirection().equals(OrderDirection.DESCENDING) ? " DESC " : " ASC ");
        } else {
            queryWithOrdering += " ORDER BY LOWER(person.apellidoNombre) ASC ";
        }*/



        return new PageImpl<AccountImpl>(
                RepositoryHelper.findByNamedParam(em,select+queryWithOrdering, paramNames, values, pageable != null ? pageable.getOffset() : null, pageable != null ? pageable.getPageSize() : null),
                pageable,
                RepositoryHelper.countByNamedParam(em,countSelect+queryWithoutOrdering, paramNames, values)
                );

    }


    private void buildWhereClause(AccountQueryFilter accountQueryFilter,
                                   List<String> wheres, List<String> paramNames, List<Object> values) {
        if (accountQueryFilter != null) {

            if (!StringUtils.isEmpty(accountQueryFilter.getName())) {
                wheres.add("  account.name = :name ");
                paramNames.add("name");
                values.add(accountQueryFilter.getName());
            }
        }
    }

}