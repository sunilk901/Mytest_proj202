package com.livestack.farmers.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user", schema = "public")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotEmpty(message = "{name.not.null}")
	@Column(name = "full_name")
	@Size(max = 50, message = "{farmer.name.size}")
	private String name;
	

	@Column(name = "created_by_user_login")
	private Integer createdByUserLogin;
	
	@JoinColumn(name = "id", referencedColumnName = "user_id")
	@OneToOne(cascade = CascadeType.ALL)
	private UserLogin userLogin;

	@JoinColumn(name = "id", referencedColumnName = "user_id")
	@OneToOne(cascade = CascadeType.ALL)
	private UserStatus userStatus;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private Set<PostalAddress> postalAddresses;

	public User() {

	}

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(name, user.name);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", name=" + name + '}';
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userLogin
	 */
	public UserLogin getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the userStatus
	 */
	public UserStatus getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return the postalAddresses
	 */
	public Set<PostalAddress> getPostalAddresses() {
		return postalAddresses;
	}

	/**
	 * @param postalAddresses the postalAddresses to set
	 */
	public void setPostalAddresses(Set<PostalAddress> postalAddresses) {
		this.postalAddresses = postalAddresses;
	}

	/**
	 * @return the createdByUserLogin
	 */
	public Integer getCreatedByUserLogin() {
		return createdByUserLogin;
	}

	/**
	 * @param createdByUserLogin the createdByUserLogin to set
	 */
	public void setCreatedByUserLogin(Integer createdByUserLogin) {
		this.createdByUserLogin = createdByUserLogin;
	}

}
