package com.livestack.farmers.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Entity
@Table(name = "farmer", schema = "public")
public class Farmer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@NotEmpty(message = "{name.not.null}")
	@Column(name = "full_name")
	@Size(max = 50, message = "{farmer.name.size}")
	private String name;

	@Column
	private String gender;

	@Column
	private String caste;

	@Column(name = "date_of_birth")
	private Date dob;

	@Column(name = "number_of_cattles")
	private Integer numberOfCattles;

	@NotNull(message = "{mobile.not.null}")
	@Size(min = 10, max = 10, message = "{mobile.size}")
	@Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "{mobile.pattern}")
	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "aadhar_number")
	@NotEmpty
	@Size(min = 12, max = 12, message = "{aadhar.size}")
	private String aadharNumber;
	
	@Transient
	Set<PostalAddress> postalAddresses;

	public Farmer() {

	}

	public Farmer(String name, String gender, String caste, String mobileNumber, Date dob, String aadharNumber,
			Set<PostalAddress> postalAddresses, Integer numberOfCattles) {
		this.name = name;
		this.gender = gender;
		this.caste = caste;
		this.mobileNumber = mobileNumber;
		this.dob = dob;
		this.aadharNumber = aadharNumber;
		this.postalAddresses = postalAddresses;
		this.numberOfCattles = numberOfCattles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, caste, mobileNumber, dob, aadharNumber, numberOfCattles);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Farmer farmer = (Farmer) o;
		return Objects.equals(id, farmer.id) && Objects.equals(name, farmer.name)
				&& Objects.equals(gender, farmer.gender) && Objects.equals(caste, farmer.caste)
				&& Objects.equals(mobileNumber, farmer.mobileNumber);
	}

	@Override
	public String toString() {
		return "Farmer{" + "id=" + id + ", name=" + name + ", gender='" + gender + '\'' + ", caste=" + caste
				+ ", mobileNumber=" + mobileNumber + '}';
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
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the caste
	 */
	public String getCaste() {
		return caste;
	}

	/**
	 * @param caste the caste to set
	 */
	public void setCaste(String caste) {
		this.caste = caste;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the numberOfCattles
	 */
	public Integer getNumberOfCattles() {
		return numberOfCattles;
	}

	/**
	 * @param numberOfCattles the numberOfCattles to set
	 */
	public void setNumberOfCattles(Integer numberOfCattles) {
		this.numberOfCattles = numberOfCattles;
	}

	/**
	 * @return the aadharNumber
	 */
	public String getAadharNumber() {
		return aadharNumber;
	}

	/**
	 * @param aadharNumber the aadharNumber to set
	 */
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
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

}
