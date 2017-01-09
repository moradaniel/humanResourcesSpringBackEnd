package org.humanResources.security.entity;




public class AccountBuilder {

    //public static final String DEFAULT_NAME = "John Smith";
    //public static final String DEFAULT_ROLE = "ROLE_USER";

    private String name;

/*    private String userName;*/
    private String password;
    /*private String preferences;
    private String fullName;
    private String email1;
    private String voice1;
    private String cell1;
    private String cell2;
    private String cell3;
    private String push1;
    private String companyName;
    private String domains;
    private String userid;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String postalCode;
    private String country;*/

    private AccountBuilder() {}

    public static AccountBuilder anAccount() {
        return new AccountBuilder();
    }


    public AccountBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AccountBuilder withPassword(String password) {
        this.password = password;
        return this;
    }
/*
    public UserBuilder withPreferences(String preferences) {
        this.preferences = preferences;
        return this;
    }
    
    public UserBuilder withFullName(String fullname) {
        this.fullName = fullname;
        return this;
    }
    
    public UserBuilder withEmail1(String email1){
        this.email1 = email1;
        return this;
    }

    public UserBuilder withVoice1(String voice1){
        this.voice1 = voice1;
        return this;
    }

    public UserBuilder withCell1(String cell1){
        this.cell1 = cell1;
        return this;
    }

    public UserBuilder withCell2(String cell2){
        this.cell2 = cell2;
        return this;
    }

    public UserBuilder withCell3(String cell3){
        this.cell3 = cell3;
        return this;
    }

    public UserBuilder withPush1(String push1){
        this.push1 = push1;
        return this;
    }

    public UserBuilder withCompanyName(String companyName){
        this.companyName = companyName;
        return this;
    }


    public UserBuilder withDomains(String domains){
        this.domains = domains;
        return this;
    }


    public UserBuilder withUserid(String userid){
        this.userid = userid;
        return this;
    }

    public UserBuilder withStreetAddress1(String streetAddress1){
        this.streetAddress1 = streetAddress1;
        return this;
    }

    public UserBuilder withStreetAddress2(String streetAddress2){
        this.streetAddress2 = streetAddress2;
        return this;
    }

    public UserBuilder withCity(String city){
        this.city = city;
        return this;
    }

    public UserBuilder withState(String state){
        this.state = state;
        return this;
    }

    public UserBuilder withCountry(String country){
        this.country = country;
        return this;
    }

    public UserBuilder withPostalCode(String postalCode){
        this.postalCode = postalCode;
        return this;
    }

/*
    public static UserBuilder aUserWithNoPassword() {
        return UserBuilder.aDefaultUser()
                .withNoPassword();
    }

    public static UserBuilder anAdmin() {
        return UserBuilder.aUser()
                .inAdminRole();
    }
*/

    public AccountImpl build() {

        AccountImpl account =  new AccountImpl();
        account.setName(this.name);
        /*
        user.setUserName(this.userName);*/
        account.setPassword(this.password);
        /*user.setPreferences(this.preferences);
        user.setFullName(this.fullName);
        user.setEmail1(this.email1);
        user.setVoice1(this.voice1);
        user.setCell1(this.cell1);
        user.setCell2(this.cell2);
        user.setCell3(this.cell3);
        user.setPush1(this.push1);
        user.setCompanyName(this.companyName);
        user.setDomains(this.domains);
        user.setUserid(this.userid);
        user.setStreetAddress1(this.streetAddress1);
        user.setStreetAddress2(this.streetAddress2);
        user.setCity(this.city);
        user.setState(this.state);
        user.setCountry(this.country);
        user.setPostalCode(this.postalCode);*/
        return account;
        
    }




}