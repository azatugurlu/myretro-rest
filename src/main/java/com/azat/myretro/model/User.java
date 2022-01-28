package com.azat.myretro.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.azat.myretro.model.base.BaseModelAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends BaseModelAudit implements UserDetails {

	private static final long serialVersionUID = -2968372744922479011L;

	@Column(name = "username")
	private String username;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "phone")
	private String phone;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "is_tfa_enabled")
	private String is_tfa_enabled;

	@Column(name = "tfa_default_type")
	private String tfa_default_type;

	@Column(name = "account_locked")
	@JsonIgnore
	private boolean accountNonLocked;

	@Column(name = "account_expired")
	@JsonIgnore
	private boolean accountNonExpired;

	@Column(name = "credentials_expired")
	@JsonIgnore
	private boolean credentialsNonExpired;

	@OneToOne(cascade = CascadeType.ALL)
	private UserAccountInformation userAccountInformation;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_user", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roles;

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountNonExpired;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountNonLocked;
	}

	/*
	 * Get roles and permissions and add them as a Set of GrantedAuthority
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
			r.getPermissions().forEach(p -> {
				authorities.add(new SimpleGrantedAuthority(p.getName()));
			});
		});

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIs_tfa_enabled() {
		return is_tfa_enabled;
	}

	public void setIs_tfa_enabled(String is_tfa_enabled) {
		this.is_tfa_enabled = is_tfa_enabled;
	}

	public String getTfa_default_type() {
		return tfa_default_type;
	}

	public void setTfa_default_type(String tfa_default_type) {
		this.tfa_default_type = tfa_default_type;
	}

	public UserAccountInformation getUserAccountInformation() {
		return userAccountInformation;
	}

	public void setUserAccountInformation(UserAccountInformation userAccountInformation) {
		this.userAccountInformation = userAccountInformation;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", phone=" + phone
				+ ", password=" + password + ", enabled=" + enabled + ", is_tfa_enabled=" + is_tfa_enabled
				+ ", tfa_default_type=" + tfa_default_type + ", accountNonLocked=" + accountNonLocked
				+ ", accountNonExpired=" + accountNonExpired + ", credentialsNonExpired=" + credentialsNonExpired
				+ ", userAccountInformation=" + userAccountInformation + ", roles=" + roles + "]";
	}

	

}
