package org.humanResources.security.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AccountRepositoryCustom {

    Page<AccountImpl> findByFilter(AccountQueryFilter accountQueryFilter,Pageable pageable);
}
