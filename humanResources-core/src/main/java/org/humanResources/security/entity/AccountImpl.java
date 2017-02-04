package org.humanResources.security.entity;

import org.humanResources.persistence.PersistentAbstract;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name	= "SEC_ACCOUNT" )
@SequenceGenerator(name="SEQ_GEN", sequenceName="SEC_ACCOUNT_SEQ", allocationSize=1)
public class AccountImpl extends PersistentAbstract implements Account, java.io.Serializable {


    @Column(name = "NAME", nullable=false, length =	200)
    private String    name;



    @Column(name = "PASSWORD", nullable=false, length =	200)
    private String    password;

    /*private Date      expire;
    private Date      expirePassword;
    private boolean   nonLocked;
    private boolean   enabled = true;*/

    /*@ManyToMany(targetEntity = RoleImpl.class)
    @JoinTable(name="SEC_ACCOUNT_ROLE",
            joinColumns={@JoinColumn(name="ACCOUNTID")},
            inverseJoinColumns={@JoinColumn(name="ROLEID")}
    )
    private List<Role> roles = new ArrayList<>();*/


    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountRoleAssociation> roles = new ArrayList<>();

    public	AccountImpl()	{}

    public	AccountImpl(Long id, String	name, String password)	{
        super();
        this.id	=	id;
        this.name	=	name;
        this.password	=	password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<AccountRoleAssociation> getRoles() {
        return roles;
    }

    public void setRoles(List<AccountRoleAssociation> roles) {
        this.roles = roles;
    }

    public void addRole(AccountRoleAssociation accountRoleAssociation) {
        this.getRoles().add(accountRoleAssociation);
    }

}
