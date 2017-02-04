package org.humanResources.security.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.humanResources.persistence.PersistentAbstract;
import org.humanResources.util.JanuxToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name	= "SEC_ROLE" )
@SequenceGenerator(name="SEQ_GEN", sequenceName="SEC_ROLE_SEQ", allocationSize=1)
public class RoleImpl extends PersistentAbstract implements Role, java.io.Serializable
{
	private static final long serialVersionUID = 2012032701;

    @Column(name = "NAME", nullable=false, length =	200)
	private String  name;

    @Column(name = "DESCRIPTION", nullable=false, length =	200)
	private String  description;

    @Column(name = "SORTORDER", nullable=false/*, length =	200*/)
	private Integer sortOrder;

    @Column(name = "ENABLED")
	private boolean enabled;


    @OneToMany(mappedBy="role",fetch = FetchType.LAZY)
    private List<AccountRoleAssociation> accounts = new ArrayList<>();


    @Transient
	private List<PermissionGranted> permissionsGrantedList;

	@Transient
	private Map<PermissionGrantedKey, Long> permissionsGranted;

    @Transient
	private List<Role> roles;

    @Transient
	private PermissionsManager permsManager;

	public RoleImpl() {}


	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/** Human readable description of this Role */
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

    public List<AccountRoleAssociation> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountRoleAssociation> accounts) {
        this.accounts = accounts;
    }

	public List<Role> getRoles() {
		if (this.roles == null) { this.roles = new ArrayList<Role>(); }
		return this.roles;
	}
	
	public void setRoles(List<Role> aggrRoles) {
		this.roles = aggrRoles;
	}



	protected Map<PermissionGrantedKey, Long> getPermissionsGranted() {

		if (this.permissionsGranted == null)
			this.permissionsGranted = new HashMap<PermissionGrantedKey, Long>();

		return this.permissionsGranted;
	}
	
	protected void setPermissionsGranted(Map<PermissionGrantedKey, Long> permissionsGranted) {
		this.permissionsGranted = permissionsGranted;
	}


	private PermissionsManager getPermissionsManager()
	{
		if (this.permsManager == null)
			this.permsManager = new PermissionsManager(this.getName(), this.getRoles(), this.getPermissionsGranted());

		return this.permsManager;
	}


	public Map<String, PermissionContext> getPermissionContexts() {
		return this.getPermissionsManager().getPermissionContexts();
	}

	public boolean hasPermissions(String permissionContext, String[] permissionNames) {
		return this.getPermissionsManager().hasPermissions(permissionContext, permissionNames);
	}

	public boolean hasPermissions(String permissionContext, String permissionName) {
		return this.getPermissionsManager().hasPermissions(permissionContext, permissionName);
	}   

	public String[] getPermissions(String permissionContext) {
		return this.getPermissionsManager().getPermissions(permissionContext);
	}

	public void grantPermissions(String permissionContext, String[] permissionNames) {
		throw new UnsupportedOperationException("grantPermissions not implemented yet");
	}

	public boolean hasPermissions(String permissionContext, long requiredPerms) { 
		return this.getPermissionsManager().hasPermissions(permissionContext, requiredPerms);
	}

	public long getPermissionsValue(String permissionContext) {
		return this.getPermissionsManager().getPermissionsValue(permissionContext);
	}

	public void grantPermissions(PermissionContext permissionContext, long permissionsGranted) {
		this.getPermissionsManager().grantPermissions(permissionContext, permissionsGranted);
	}

	public void denyPermissions(PermissionContext permissionContext, long permissionsGranted) {
		this.getPermissionsManager().denyPermissions(permissionContext, permissionsGranted);
	}

	public Long getPermissionsGranted(String permissionSetName) {
		throw new UnsupportedOperationException("getPermissionsGranted(permissionSetName) has not yet been implemented");
	}
	
	public void setPermissionsGranted(String permissionSetName, Long permissions) {
		throw new UnsupportedOperationException(
				"setPermissionsGranted(permissionSetName, permissions) has not yet been implemented");
	}
	

	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}


	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


    public void addAccount(AccountRoleAssociation accountRoleAssociation) {
        this.getAccounts().add(accountRoleAssociation);
    }

	public String toString() 
	{ 
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		sb.append("id", getId())
			.append("name", getName())
			.append("enabled", isEnabled());

		if (getSortOrder() != null) sb.append("sortOrder", getSortOrder());

		sb.append("permsGranted", getPermissionsGranted());

		if (this.getRoles().size() > 0) sb.append("roles", getRoles());
		
		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof RoleImpl) ) return false;
		Role castOther = (Role)other; 

		return new EqualsBuilder()
			.append(this.getName(), castOther.getName())
			.isEquals();
	}


	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getName())
		.toHashCode();
	}


} // end class RoleImpl
