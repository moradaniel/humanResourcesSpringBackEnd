package org.humanResources.security.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SEC_ACCOUNT_ROLE")
public class AccountRoleAssociation implements Serializable{


    private AccountImpl account;
    private RoleImpl role;


    @Column(name = "SORTORDER", nullable = false)
    private Integer sortOrder;


    @Id
    @ManyToOne
    @JoinColumn(name = "ACCOUNTID")
    public AccountImpl getAccount() {
        return account;
    }

    public void setAccount(AccountImpl account) {
        this.account = account;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "ROLEID")
    public RoleImpl getRole() {
        return role;
    }

    public void setRole(RoleImpl role) {
        this.role = role;
    }

    @Column(name = "SORTORDER")
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }


    /*
    @ManyToOne
    @JoinColumn(name = "ACCOUNTID", updatable = false, insertable = false, referencedColumnName = "ID")
    //@JoinColumn(name = "ACCOUNTID")
    private AccountImpl account;

    @ManyToOne
    @JoinColumn(name = "ROLEID", updatable = false, insertable = false, referencedColumnName = "ID")
    //@JoinColumn(name = "ROLEID")
    private RoleImpl role;


    public AccountImpl getAccount() {
        return account;
    }

    public void setAccount(AccountImpl account) {
        this.account = account;
    }

    public RoleImpl getRole() {
        return role;
    }

    public void setRole(RoleImpl role) {
        this.role = role;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
*/
}