package com.livestack.farmers.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_postal_address", schema = "public")
public class PostalAddress implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@Column(name = "user_id", insertable = false, updatable = false)
//	private Integer userId;

	@Id
	@GeneratedValue
	@Column(name = "address_id")
	private Integer addressId;

	@Column(name = "address_type_id")
	private String addressTypeId;

	@Column(name = "address")
	@Size(max = 150, message = "{address.size}")
	private String address;

	@Column(name = "state_geo_id")
	private String state;

	@Column(name = "district")
	private String district;

	@Column(name = "tehsil")
	private String tehsil;

	@Column(name = "village")
	private String village;

	@Column(name = "hamlet")
	private String hamlet;

	public PostalAddress() {

	}

	public PostalAddress(Integer userId, Integer addressId, String addressTypeId, String address, String state, String district,
			String tehsil, String village, String hamlet) {
		this.addressId = addressId;
		this.addressTypeId = addressTypeId;
		this.address = address;
		this.state = state;
		this.district = district;
		this.tehsil = tehsil;
		this.village = village;
		this.hamlet = hamlet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(addressId, addressTypeId, address, state, district, tehsil, village, hamlet);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PostalAddress postalAddress = (PostalAddress) o;
		return Objects.equals(addressId, postalAddress.addressId)
				&& Objects.equals(addressTypeId, postalAddress.addressTypeId)
				&& Objects.equals(address, postalAddress.address) && Objects.equals(state, postalAddress.state)
				&& Objects.equals(district, postalAddress.district) && Objects.equals(tehsil, postalAddress.tehsil)
				&& Objects.equals(village, postalAddress.village) && Objects.equals(hamlet, postalAddress.hamlet);
	}

	@Override
	public String toString() {
		return "PostalAddresses{" + "addressId=" + addressId + ", addressTypeId=" + addressTypeId + ", address="
				+ address + ", state=" + state + ", district=" + district + ", tehsil=" + tehsil + ", village="
				+ village + ", hamlet= " + hamlet + '}';
	}

//	/**
//	 * @return the userId
//	 */
//	public Integer getUserId() {
//		return userId;
//	}
//
//	/**
//	 * @param userId the userId to set
//	 */
//	public void setUserId(Integer userId) {
//		this.userId = userId;
//	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the stateGeoId
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param stateGeoId the stateGeoId to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the tehsil
	 */
	public String getTehsil() {
		return tehsil;
	}

	/**
	 * @param tehsil the tehsil to set
	 */
	public void setTehsil(String tehsil) {
		this.tehsil = tehsil;
	}

	/**
	 * @return the village
	 */
	public String getVillage() {
		return village;
	}

	/**
	 * @param village the village to set
	 */
	public void setVillage(String village) {
		this.village = village;
	}

	/**
	 * @return the hamlet
	 */
	public String getHamlet() {
		return hamlet;
	}

	/**
	 * @param hamlet the hamlet to set
	 */
	public void setHamlet(String hamlet) {
		this.hamlet = hamlet;
	}

	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the addressTypeId
	 */
	public String getAddressTypeId() {
		return addressTypeId;
	}

	/**
	 * @param addressTypeId the addressTypeId to set
	 */
	public void setAddressTypeId(String addressTypeId) {
		this.addressTypeId = addressTypeId;
	}



}
