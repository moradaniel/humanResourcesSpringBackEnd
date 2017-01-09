package org.humanResources.security.entity;

import javax.persistence.*;


@Entity
@Table(name	= "SEC_ACCOUNT" )
public class AccountImpl implements Account {


    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="SEC_ACCOUNT_SEQ", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    private	Long	id;

    @Column(name = "NAME", length =	200)
    private String    name;



    @Column(name = "PASSWORD", length =	200)
    private String    password;

    /*private Date      expire;
    private Date      expirePassword;
    private boolean   nonLocked;
    private boolean   enabled = true;*/



    public	AccountImpl()	{}

    public	AccountImpl(Long	id,	String	name, String password)	{
        super();
        this.id	=	id;
        this.name	=	name;
        this.password	=	password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
