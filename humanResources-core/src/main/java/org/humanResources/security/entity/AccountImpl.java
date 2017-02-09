package org.humanResources.security.entity;

import org.humanResources.persistence.PersistentAbstract;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name	= "SEC_ACCOUNT" )
@SequenceGenerator(name="SEQ_GEN", sequenceName="SEC_ACCOUNT_SEQ", allocationSize=1)
public class AccountImpl extends PersistentAbstract implements Account, java.io.Serializable {


    @Column(name = "NAME", nullable=false, length =	200)
    private String    name;

    @Column(name = "PASSWORD", nullable=false, length =	200)
    private String    password;

    @Temporal(TemporalType.DATE)
    @Column(name="EXPIRE")
    private Date expire;

    @Temporal(TemporalType.DATE)
    @Column(name="EXPIREPASSWORD")
    private Date   expirePassword;

    @Column(name = "UNLOCKED", nullable = false)
    private boolean   nonLocked = true;

    @Column(name = "ENABLED", nullable = false)
    private boolean   enabled = true;

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

    public String getUsername() {
        return this.getName();
    }

    public void setUsername(String name) {
        this.setName(name);
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


    public boolean isAccountNonLocked() {
        return this.nonLocked;
    }

    public void setAccountNonLocked(boolean b) {
        this.nonLocked = b;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired()
    {
        if (this.getExpiration() != null)
            return this.getExpiration().after(new Date());
        else
            return true;
    }

    public Date getExpiration() {
        return this.expire;
    }

    public void setExpiration(Date date) {
        this.expire = date;
    }

    public Date getPasswordExpiration() {
        return expirePassword;
    }

    public void setPasswordExpiration(Date date) {
        this.expirePassword = date;
    }


    public boolean isCredentialsNonExpired()
    {
        if (this.getPasswordExpiration() != null)
            return this.getPasswordExpiration().after(new Date());
        else
            return true;
    }
}
