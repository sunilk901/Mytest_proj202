package com.livestack.farmers.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_status", schema = "public")
public class UserStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "from_date")
    private Timestamp fromDate;
	
	@Column(name = "thru_date")
    private Timestamp thruDate;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	public UserStatus() {

	}

	public UserStatus(Integer userId, Timestamp fromDate, Timestamp thruDate, boolean isActive) {
		this.userId = userId;
		this.fromDate = fromDate;
		this.thruDate = thruDate;
		this.isActive = isActive;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(userId, fromDate, thruDate, isActive);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserStatus userStatus = (UserStatus) o;
		return Objects.equals(userId, userStatus.userId) && Objects.equals(fromDate, userStatus.fromDate)
				&& Objects.equals(thruDate, userStatus.thruDate) && Objects.equals(isActive, userStatus.isActive);
	}

	@Override
	public String toString() {
		return "UserStatus{" + "userId=" + userId + ", fromDate=" + fromDate + ", thruDate='" + thruDate + '\'' + ", isActive=" + isActive + '}';
	}

	
	/**
	 * @return the fromDate
	 */
	public Timestamp getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the thruDate
	 */
	public Timestamp getThruDate() {
		return thruDate;
	}

	/**
	 * @param thruDate the thruDate to set
	 */
	public void setThruDate(Timestamp thruDate) {
		this.thruDate = thruDate;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
