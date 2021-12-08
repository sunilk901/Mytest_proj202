package com.livestack.farmers.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.livestack.farmers.domain.Farmer;
import com.livestack.farmers.domain.UserStatus;

@Repository
public interface UserStatusRepo extends JpaRepository<UserStatus, Integer> {
	
	@Query("UPDATE UserStatus u SET u.thruDate = ?1, u.isActive =?2 WHERE u.userId = ?3")
    @Modifying
    public void disableUser(Timestamp thruDate, Boolean isActive, Integer id);
	
    @Query("SELECT u FROM UserStatus u WHERE (u.thruDate is null and u.isActive = true)")
    public List<UserStatus> getValidUsers();

}
